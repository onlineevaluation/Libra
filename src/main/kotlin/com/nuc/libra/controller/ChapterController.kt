package com.nuc.libra.controller

import com.nuc.libra.po.Course
import com.nuc.libra.result.Result
import com.nuc.libra.service.ChapterService
import com.nuc.libra.util.ResultUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * @author 杨晓辉 2019/4/11 15:25
 */

@RestController
@RequestMapping(value = ["/topic", "/chapter"])
class ChapterController {

    @Autowired
    private lateinit var chapterService: ChapterService

    // todo 只需要一个id 为什么要传入整个类的参数
    @GetMapping(value = ["/getTopic", "/getChapter"])
    fun getChapter(course: Course): Result {
        val chapterList = chapterService.getTopicByCourseId(course)
        return ResultUtils.success(data = chapterList)
    }


    @GetMapping(" /getKnowledge")
    fun getKnowledge(course: Course): Result {
        val list = chapterService.getKnowledgeByChapterId(course)
        return ResultUtils.success(data = list)

    }

}