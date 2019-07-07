package com.nuc.libra.service.impl

import com.nuc.libra.exception.ResultException
import com.nuc.libra.po.Class
import com.nuc.libra.po.StudentScore
import com.nuc.libra.repository.*
import com.nuc.libra.service.ClassService
import com.nuc.libra.vo.*
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.BeanUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

/**
 * @author 杨晓辉 2019/4/3 18:13
 */
@Service
class ClassServiceImpl : ClassService {

    private val logger: Logger = LoggerFactory.getLogger(this.javaClass)


    @Autowired
    private lateinit var classRepository: ClassRepository

    @Autowired
    private lateinit var studentScoreRepository: StudentScoreRepository

    @Autowired
    private lateinit var studentRepository: StudentRepository

    @Autowired
    private lateinit var courseClassRepository: CourseClassRepository

    /**
     * 通过教师 id 获取该教师所教授班级
     * @param teacherId 教师 id
     */
    override fun listAllClassByTeacherId(teacherId: Long): List<ClassInfo> {
        val list = courseClassRepository.findByTeacherId(teacherId)
        val classInfoList = list.distinctBy { it.classId }.map {
            val `class` = classRepository.findById(it.classId).get()
            ClassInfo(`class`.id, `class`.name)
        }
        logger.info("class list is $classInfoList")
        return classInfoList
    }

    /**
     * 成绩分析
     * @param classId Long
     * @param pageId Long
     * @return Map<Int, List<StudentScore>>
     */
    override fun scoreAnalytics(classId: Long, pageId: Long): Map<Int, List<StudentScore>> {

        return studentRepository.findStudentsByClassId(classId).mapNotNull {
            studentScoreRepository.findStudentScoresByStudentIdAndPagesId(it.id, pageId)
        }.groupBy {
            when (it.score) {
                in 0.0..59.99 -> {
                    50
                }
                in 60.0..69.99 -> {
                    60
                }
                in 70.0..79.99 -> {
                    70
                }
                in 80.0..89.99 -> {
                    80
                }
                in 90.0..99.99 -> {
                    90
                }
                100.0 -> {
                    100
                }
                else -> {
                    logger.info("other is $it")
                    2
                }
            }
        }

    }

    /**
     * 获取班级前 10 名
     * @param classId Long 班级id
     * @param teacherId Long 教师id
     * @param pageId Long 试卷id
     * @return List<StudentAndScoreInfo> 返回前 10 名 成绩
     */
    override fun classTop10(classId: Long, teacherId: Long, pageId: Long): List<StudentAndScoreInfo> {
        // 检查合法性
        courseClassRepository.findByTeacherIdAndClassId(teacherId, classId)
                ?: throw ResultException("该教师不教授该班级", 500)
        // 获取该班级所有学生
        val studentList = studentRepository.findStudentsByClassId(classId)
        // 获取前十名
        // 名次计数
        var index = 0
        val list = studentList.mapNotNull { student ->
            studentScoreRepository.findStudentScoresByStudentIdAndPagesId(student.id, pageId)
        }


        val length = if (list.size < 10) {
            list.size
        } else {
            10
        }

        return list.sortedByDescending { it.score }.subList(0, length).map {
            val studentAndScoreInfo = StudentAndScoreInfo()
            BeanUtils.copyProperties(it, studentAndScoreInfo)
            val student = studentRepository.findById(it.studentId).get()
            studentAndScoreInfo.pageId = pageId
            studentAndScoreInfo.studentName = student.name
            studentAndScoreInfo.studentNumber = student.studentNumber
            index++
            studentAndScoreInfo.index = index
            return@map studentAndScoreInfo
        }
    }

    /**
     * 获取该教师 所教授的学生总数
     * @param teacherId Long 教师id
     * @return Long 学生总数
     */
    override fun studentCount(teacherId: Long): Long {
        val classAndTeachers = courseClassRepository.findByTeacherId(teacherId)
        return classAndTeachers.distinctBy { it.classId }.map {
            studentRepository.countByClassId(classId = it.classId)
        }.reduce { sum, count -> sum + count }
    }


    /**
     * 查询该教师所有班级的班级的人数
     * @param teacherId Long 教师id
     * @return List<ClassStudentCountInfo>
     */
    override fun studentCountByClass(teacherId: Long): List<ClassStudentCountInfo> {

        return courseClassRepository.findByTeacherId(teacherId).distinctBy { it.classId }.map {
            val count = studentRepository.countByClassId(it.classId)
            return@map ClassStudentCountInfo().apply {
                this.classId = it.classId
                this.classNumber = classRepository.findById(it.classId).get().name
                this.membersCount = count
            }
        }
    }

    /**
     * 计算及格率
     * @param classId Long 班级id
     * @param pageId Long 试卷id
     * @return Double 保留三位小数
     */
    override fun studentPassedInClass(classId: Long, pageId: Long): Double {

        val studentList = studentRepository.findStudentsByClassId(classId)
        val classmateList = studentList.mapNotNull { student ->
            studentScoreRepository.findStudentScoresByStudentIdAndPagesId(student.id, pageId)
        }
        val classmateCount = classmateList.size
        val passedCount = classmateList.filter {
            it.score >= 60.0
        }.count()

        val rateDouble = (passedCount * 1.0 / classmateCount).coerceIn(0.0, 1.0)
        return String.format("%.3f", rateDouble).toDouble()
    }

    /**
     * 获取班级学生成绩
     * @param classId Long 班级id
     * @param pageId Long 学生id
     * @return List<StudentAndScoreInfo>
     */
    override fun listStudentScoreByClassId(classId: Long, pageId: Long): List<StudentAndScoreInfo> {
        val studentList = studentRepository.findStudentsByClassId(classId)
        var index = 0
        val studentAndScoreInfoList = studentList.map { student ->
            val studentAndScore = studentScoreRepository.findByPagesIdAndStudentId(pageId, student.id)
            val studentAndScoreInfo = StudentAndScoreInfo()
            studentAndScoreInfo.studentNumber = student.studentNumber
            studentAndScoreInfo.studentId = student.id
            studentAndScoreInfo.studentName = student.name
            // 缺考
            studentAndScoreInfo.score = -1.0
            if (studentAndScore != null) {
                studentAndScoreInfo.score = studentAndScore.score
                studentAndScoreInfo.pageId = pageId
                studentAndScoreInfo
            } else {
                studentAndScoreInfo
            }
        }.sortedByDescending {
            it.score
        }.map { studentAndScoreInfo ->
            index++
            studentAndScoreInfo.index = index
            return@map studentAndScoreInfo
        }
        return studentAndScoreInfoList
    }

    /**
     * 获取所有班级
     * @return List<Class>
     */
    override fun getAllClass(): MutableList<Class> {
        return classRepository.findAll()
    }

    @Autowired
    private lateinit var pageRepository: PagesRepository

    /**
     * 获取学生平均成绩
     * @param classId Long
     * @param courseId Long
     * @return List<StudentAvgInfo>
     */
    override fun avgScoreByClassId(classId: Long, courseId: Long): List<StudentAvgInfo> {
        val studentList = studentRepository.findStudentsByClassId(classId)
        val pageList = pageRepository.findByCourseId(courseId)
        val `class` = classRepository.getOne(classId)
        val studentAvgInfoList = ArrayList<StudentAvgInfo>()
        studentList.forEach { student ->
            val scoreList = ArrayList<Double>()
            pageList.forEach { page ->

                logger.info("student id =${student.id} page id = ${page.id} ")
                val studentScore = studentScoreRepository.findByPagesIdAndStudentId(page.id, student.id)
                if (studentScore != null) {
                    scoreList.add(studentScore.score)
                }
            }
            var average = scoreList.sum() / scoreList.size
            if (average.isNaN()) {
                average = 0.0
            }
            val studentInfo = StudentInfo()
            studentInfo.`class` = `class`.name
            studentInfo.classId = classId
            studentInfo.name = student.name
            studentInfo.studentNumber = student.studentNumber

            studentAvgInfoList.add(StudentAvgInfo().apply {
                this.average = average.coerceIn(0.00, 100.00)
                this.student = studentInfo
                this.courseId = courseId
            })
        }
        return studentAvgInfoList
    }

}

