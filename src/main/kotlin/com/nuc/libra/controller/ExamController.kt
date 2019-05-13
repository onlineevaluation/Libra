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


    /**
     * 获取该班级的学生错误信息
     * @param classId  classId: Long 班级id
     * @param pageId  pageId: Long 试卷id
     * @return Result
     */
    @GetMapping("/error/{classId}/{pageId}")
    fun getErrorInfo(@PathVariable("classId") classId: Long, @PathVariable("pageId") pageId: Long): Result {
        val studentErrorInfo = examService.getStudentErrorInfo(classId, pageId)
        return ResultUtils.success(data = studentErrorInfo)
    }


    /**
     * 获取试题概况
     * @param classId ") classId: Long
     * @param pageId ") pageId: Long
     * @return Result
     */
    @GetMapping("/class/{classId}/{pageId}")
    fun getClassErrorInfo(@PathVariable("classId") classId: Long, @PathVariable("pageId") pageId: Long): Result {
        val studentAllErrorTitleKnowledge = examService.getStudentAllErrorTitleKnowledge(classId, pageId)
        return ResultUtils.success(data = studentAllErrorTitleKnowledge)
    }
}