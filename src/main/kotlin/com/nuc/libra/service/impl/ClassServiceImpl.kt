package com.nuc.libra.service.impl

import com.nuc.libra.exception.ResultException
import com.nuc.libra.repository.ClassAndTeacherRepository
import com.nuc.libra.repository.ClassRepository
import com.nuc.libra.repository.StudentRepository
import com.nuc.libra.repository.StudentScoreRepository
import com.nuc.libra.service.ClassService
import com.nuc.libra.vo.ClassInfo
import com.nuc.libra.vo.ClassStudentCountInfo
import com.nuc.libra.vo.StudentAndScoreInfo
import org.springframework.beans.BeanUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

/**
 * @author 杨晓辉 2019/4/3 18:13
 */
@Service
class ClassServiceImpl : ClassService {

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
            classList.add(ClassInfo(`class`.id, `class`.num))
        }
        return classList
    }

    override fun scoreAnalytics(classId: Long, courseId: Long, pageId: Long) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
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
        return studentList.mapNotNull { student ->
            studentScoreRepository.findStudentScoresByStudentIdAndPagesId(student.id, pageId)
        }.sortedByDescending { it.score }.subList(0, 10).map {
            val studentAndScoreInfo = StudentAndScoreInfo()
            BeanUtils.copyProperties(it, studentAndScoreInfo)
            val student = studentRepository.findById(it.studentId).get()
            studentAndScoreInfo.studentName = student.name!!
            studentAndScoreInfo.studentNumber = student.studentNumber
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
                this.classNumber = classRepository.findById(it.classId).get().num
                this.count = count
            }
        }
    }
}
