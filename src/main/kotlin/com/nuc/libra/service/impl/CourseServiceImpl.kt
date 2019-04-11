package com.nuc.libra.service.impl

import com.nuc.libra.po.Course
import com.nuc.libra.service.CourseService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

/**
 * @author 杨晓辉 2019/4/11 14:57
 */
@Service
class CourseServiceImpl : CourseService {

    @Autowired
    private lateinit var courseService: CourseService

    override fun getAllCourse(): List<Course> {
        return courseService.getAllCourse()
    }

}