package com.nuc.libra.service

import com.nuc.libra.po.Course
import org.springframework.stereotype.Service

/**
 * @author 杨晓辉 2019/4/11 14:56
 */
@Service
interface CourseService{


    /**
     * 获取所有的课程
     * @return List<Course>
     */
    fun getAllCourse():List<Course>

    /**
     * 通过教师id获取所有的课程
     * @param teacherId 教师id
     * @return 该教师所教授的课程
     */
    fun getAllCourseByTeacherId(teacherId:Long):List<Course>
}