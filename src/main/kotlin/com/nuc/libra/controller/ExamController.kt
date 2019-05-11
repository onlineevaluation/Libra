package com.nuc.libra.controller

import com.nuc.libra.result.Result
import com.nuc.libra.service.ExamService
import com.nuc.libra.util.ResultUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * @author 杨晓辉 2019/5/9 9:24
 */
@RestController
@RequestMapping("/exam")
class ExamController {

    @Autowired
    private lateinit var examService: ExamService

    /**
     * 获取学生总考试
     * @param studentId  studentId: Long
     * @return Result
     */
    @GetMapping("/count/{studentId}")
    fun getClassExamCount(@PathVariable("studentId") studentId: Long): Result {
        val count = examService.getClassExamPageCount(studentId)
        return ResultUtils.success(data = count)
    }

    @GetMapping("/error/{classId}/{pageId}")
    fun getErrorInfo(@PathVariable("classId") classId: Long, @PathVariable("pageId") pageId: Long) {
        examService.getStudentErrorInfo(classId, pageId)
    }


}