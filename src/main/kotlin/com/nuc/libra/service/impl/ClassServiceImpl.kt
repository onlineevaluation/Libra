package com.nuc.libra.service.impl

import com.nuc.libra.exception.ResultException
import com.nuc.libra.po.Class
import com.nuc.libra.po.StudentScore
import com.nuc.libra.repository.ClassAndTeacherRepository
import com.nuc.libra.repository.ClassRepository
import com.nuc.libra.repository.StudentRepository
import com.nuc.libra.repository.StudentScoreRepository
import com.nuc.libra.service.ClassService
import com.nuc.libra.vo.ClassInfo
import com.nuc.libra.vo.ClassStudentCountInfo
import com.nuc.libra.vo.StudentAndScoreInfo
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
    private lateinit var classAndTeacherRepository: ClassAndTeacherRepository

    @Autowired
    private lateinit var classRepository: ClassRepository

    @Autowired
    private lateinit var studentScoreRepository: StudentScoreRepository

    @Autowired
    private lateinit var studentRepository: StudentRepository

    /**
     * 通过教师 id 获取该教师所教授班级
     * @param teacherId 教师 id
     */
    override fun listAllClassByTeacherId(teacherId: Long): List<ClassInfo> {
        val classList = ArrayList<ClassInfo>()
        val list =
            classAndTeacherRepository.findByTeacherId(teacherId)

        list.forEach {
            val `class` = classRepository.findById(it.classId).get()
            classList.add(ClassInfo(`class`.id, `class`.name))
        }
        return classList
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
        classAndTeacherRepository.findClassAndTeacherByTeacherIdAndClassId(teacherId, classId)
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
        val classAndTeachers = classAndTeacherRepository.findByTeacherId(teacherId)
        return classAndTeachers.map {
            studentRepository.countByClassId(classId = it.classId)
        }.reduce { sum, count -> sum + count }
    }


    /**
     * 查询该教师所有班级的班级的人数
     * @param teacherId Long 教师id
     * @return List<ClassStudentCountInfo>
     */
    override fun studentCountByClass(teacherId: Long): List<ClassStudentCountInfo> {

        return classAndTeacherRepository.findByTeacherId(teacherId).map {
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
     * @param classId Long
     * @param pageId Long
     * @return Double
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

        return (passedCount * 1.0 / classmateCount)
    }

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
    override fun getAllClass(): List<Class> {
        return classRepository.findAll()
    }

}

