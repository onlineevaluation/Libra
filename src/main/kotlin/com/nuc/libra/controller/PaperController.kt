package com.nuc.libra.controller

import com.nuc.libra.result.Result
import com.nuc.libra.service.PaperService
import com.nuc.libra.util.ResultUtils
import com.nuc.libra.vo.ArtificialPaperParam
import com.nuc.libra.vo.PaperTitleTypeParam
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * @author 杨晓辉 2019/4/18 11:37
 * 主要负责试卷生成
 */
@RestController
@RequestMapping("/paper")
class PaperController {

    @Autowired
    private lateinit var paperService: PaperService

    /**
     * 提交试卷参数信息
     * @param paperTitleTypeParam PaperTitleTypeParam 试卷参数信息
     * @return Result
     */
    @PostMapping("/info")
    fun setPaperInfo(@RequestBody paperTitleTypeParam: PaperTitleTypeParam): Result {
        val titles = paperService.getTitles(
            paperTitleTypeParam.courseId,
            paperTitleTypeParam.titleType,
            paperTitleTypeParam.chapterIds
        )
        return ResultUtils.success(data = titles)
    }

    /**
     * 手动生成试卷
     */
    @PostMapping("/artificial")
    fun artificialPaper(@RequestBody artificialPaperParam: ArtificialPaperParam): Result {
        println("artificial is $artificialPaperParam")

        return ResultUtils.success()
    }
}
