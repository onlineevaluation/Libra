package com.nuc.libra.controller

import com.nuc.libra.result.Result
import com.nuc.libra.service.CourseService
import com.nuc.libra.util.ResultUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
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

    /**
     * 通过教师id获取教师所教授的课程
     * @param teacherId Long 教师id
     * @return Result
     */
    @GetMapping("/teacher/{teacherId}")
    fun teachersCourse(@PathVariable("teacherId") teacherId: Long): Result {
        val allCourseByTeacherId = courseService.getAllCourseByTeacherId(teacherId)
        return ResultUtils.success(data = allCourseByTeacherId)
    }
}
