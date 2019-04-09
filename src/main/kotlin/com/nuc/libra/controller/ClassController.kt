package com.nuc.libra.controller

import com.nuc.libra.result.Result
import com.nuc.libra.service.ClassService
import com.nuc.libra.util.ResultUtils
import com.nuc.libra.vo.ClassAndPageParam
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

/**
 * @author 杨晓辉 2019/4/3 18:10
 */
@RestController
@RequestMapping("/class")
class ClassController {

    @Autowired
    private lateinit var classService: ClassService

    /**
     * 通过教师 id 获取班级列表
     */
    @GetMapping("/teacher/{teacherId}")
    fun getClass(@PathVariable(name = "teacherId") teacherId: Long): Result {
        val listAllClass = classService.listAllClassByTeacherId(teacherId)
        return ResultUtils.success(data = listAllClass)
    }

    /**
     * 分数统计
     */
    @GetMapping("/classScore/{classId}")
    fun scoreAnalytics(@PathVariable(name = "classId") classId: Long): Result {
        return ResultUtils.success()
    }

    /**
     * 获取班级前十名
     * @param classAndPageParam ClassAndPageParam
     * @return Result
     */
    @GetMapping("/top10")
    fun top10InClass(@RequestBody classAndPageParam: ClassAndPageParam): Result {

        val top10List =
            classService.classTop10(classAndPageParam.classId, classAndPageParam.teacherId, classAndPageParam.pageId)

        return ResultUtils.success(data = top10List)
    }

    /**
     * 获取教师所教授班级和班级人数
     * @param teacherId Long
     * @return Result
     */
    @GetMapping("/teached/{teacherId}")
    fun teachClass(@PathVariable(name = "teacherId") teacherId: Long): Result {

        val countByClass = classService.studentCountByClass(teacherId)
        return ResultUtils.success(data = countByClass)
    }

}