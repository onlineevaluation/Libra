package com.nuc.libra.service

import com.nuc.libra.vo.ClassInfo
import com.nuc.libra.vo.StudentAndScoreInfo
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
    fun scoreAnalytics(classId: Long, courseId: Long, pageId: Long)

    /**
     * 获取班级前十名
     */
    fun classTop10(classId: Long, teacherId: Long, pageId:Long):List<StudentAndScoreInfo>

    /**
     * 教师所教授的班级总人数
     */
    fun studentCount(teacherId: Long)

}