package com.nuc.libra.controller

import com.nuc.libra.result.Result
import com.nuc.libra.service.CourseService
import com.nuc.libra.util.ResultUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * @author 杨晓辉 2019/4/11 14:59
 */
@RestController
@RequestMapping("/course")
class CourseController {

    @Autowired
    private lateinit var courseService: CourseService


    /**
     * 获取所有的课程
     * @return Result
     */
    @GetMapping("/getCourse")
    fun getCourse(): Result {
        val courseList = courseService.getAllCourse()
        return ResultUtils.success(data = courseList)
    }
}
