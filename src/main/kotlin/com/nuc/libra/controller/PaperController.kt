package com.nuc.libra.controller

import com.nuc.libra.result.Result
import com.nuc.libra.service.PaperService
import com.nuc.libra.util.ResultUtils
import com.nuc.libra.vo.PaperTitleTypeParam
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * @author 杨晓辉 2019/4/18 11:37
 */
@RestController
@RequestMapping("/paper")
class PaperController {

    @Autowired
    private lateinit var paperService: PaperService

    @PostMapping("/info")
    fun setPaperInfo(@RequestBody paperTitleTypeParam: PaperTitleTypeParam): Result {
        println(paperTitleTypeParam.toString())
        val titles = paperService.getTitles(paperTitleTypeParam.courseId, paperTitleTypeParam.titleType,paperTitleTypeParam.chapterIds)
        return ResultUtils.success(data = titles)
    }

}
