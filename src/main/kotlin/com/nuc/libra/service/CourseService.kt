package com.nuc.libra.service

import com.nuc.libra.po.Course
import org.springframework.stereotype.Service

/**
 * @author 杨晓辉 2019/4/11 14:56
 */
@Service
interface CourseService{


    fun getAllCourse():List<Course>

}