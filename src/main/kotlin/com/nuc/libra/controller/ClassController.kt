package com.nuc.libra.controller

import com.nuc.libra.po.Class
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
    @GetMapping("/classScore/{classId}/{pageId}")
    fun scoreAnalytics(@PathVariable(name = "classId") classId: Long, @PathVariable("pageId") pageId: Long)
            : Result {
        val analytics = classService.scoreAnalytics(classId, pageId)
        return ResultUtils.success(data = analytics)
    }

    /**
     * 获取班级前十名
     * @param classAndPageParam ClassAndPageParam
     * @return Result
     */
    @GetMapping("/top10")
    fun top10InClass(classAndPageParam: ClassAndPageParam): Result {

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

    /**
     * 所教授学生总数
     * @param teacherId ") teacherId: Long
     * @return Result
     */
    @GetMapping("/students/teacher/{teacherId}")
    fun allStudentCount(@PathVariable(name = "teacherId") teacherId: Long): Result {
        val count = classService.studentCount(teacherId)
        return ResultUtils.success(data = count)
    }

    /**
     * 及格率获取
     * @param pageId ") pageId: Long
     * @param classId ") classId: Long
     * @return Result
     */
    @GetMapping("/passed/{pageId}/{classId}")
    fun passedRate(@PathVariable(name = "pageId") pageId: Long, @PathVariable(name = "classId") classId: Long): Result {
        val passedRate = classService.studentPassedInClass(classId, pageId)
        return ResultUtils.success(message = "及格率", data = passedRate)
    }


    /**
     * 获取所有班级
     *
     * @return Result 返回结果集
     */
    @GetMapping("/getClass")
    fun getClazz(): Result {
        val clazz = Class()
        clazz.id = 0
        clazz.name = "全部"
        val classes = classService.getAllClass()
        classes.add(0, clazz)
        return ResultUtils.success(200, "已经返回所有班级信息", classes)
    }
}