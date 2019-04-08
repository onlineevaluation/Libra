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
    fun getClass(@PathVariable teacherId: Long): Result {
        val listAllClass = classService.listAllClassByTeacherId(teacherId)
        return ResultUtils.success(data = listAllClass)
    }

    /**
     * 分数统计
     */
    @GetMapping("/classScore/{classId}")
    fun scoreAnalytics(@PathVariable classId: Long): Result {
        return ResultUtils.success()
    }


    @GetMapping("/top10")
    fun top10InClass(@RequestBody classAndPageParam: ClassAndPageParam): Result {

        val top10List =
            classService.classTop10(classAndPageParam.classId, classAndPageParam.teacherId, classAndPageParam.pageId)

        return ResultUtils.success(data = top10List)
    }

}