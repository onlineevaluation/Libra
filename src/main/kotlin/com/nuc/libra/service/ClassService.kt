package com.nuc.libra.service

import com.nuc.libra.po.Class
import com.nuc.libra.po.StudentScore
import com.nuc.libra.vo.ClassInfo
import com.nuc.libra.vo.ClassStudentCountInfo
import com.nuc.libra.vo.StudentAndScoreInfo
import com.nuc.libra.vo.StudentAvgInfo
import org.springframework.stereotype.Service

/**
 * @author 杨晓辉 2019/4/3 18:13
 */
@Service
interface ClassService {

    /**
     * 通过教师 id 获取该教师所教授班级
     * @param teacherId 教师 id
     */
    fun listAllClassByTeacherId(teacherId: Long): List<ClassInfo>

    /**
     * 班级成绩分析
     */
    fun scoreAnalytics(classId: Long, pageId: Long): Map<Int, List<StudentScore>>

    /**
     * 获取班级前十名
     */
    fun classTop10(classId: Long, teacherId: Long, pageId: Long): List<StudentAndScoreInfo>

    /**
     * 教师所教授的班级总人数
     * @param teacherId 教师id
     */
    fun studentCount(teacherId: Long): Long

    /**
     * 查询教师班级所带人数
     * @param teacherId Long 教师id
     * @return List<ClassStudentCountInfo>
     */
    fun studentCountByClass(teacherId: Long): List<ClassStudentCountInfo>

    /**
     * 学生及格率
     * @return Double 及格率
     */
    fun studentPassedInClass(classId: Long, pageId: Long): Double

    /**
     * 获取该班级单张试卷所有的学生成绩
     * @param classId Long 班级id
     * @param pageId Long 试卷id
     * @return List<StudentAndScoreInfo>
     */
    fun listStudentScoreByClassId(classId: Long, pageId: Long): List<StudentAndScoreInfo>

    /**
     * 获取所有的班级
     * @return MutableList<Class>
     */
    fun getAllClass(): MutableList<Class>

    /**
     * 计算平均成绩
     * @param classId Long
     * @return Double
     */
    fun avgScoreByClassId(classId: Long, courseId: Long): List<StudentAvgInfo>
}