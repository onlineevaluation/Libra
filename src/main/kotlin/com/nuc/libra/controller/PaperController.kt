package com.nuc.libra.controller

import com.nuc.libra.result.Result
import com.nuc.libra.service.PaperService
import com.nuc.libra.util.ResultUtils
import com.nuc.libra.vo.ArtificialPaperParam
import com.nuc.libra.vo.PageClassParam
import com.nuc.libra.vo.PaperTitleTypeParam
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

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
        val pageInfo = paperService.artificial(artificialPaperParam)
        return ResultUtils.success(data = pageInfo)
    }

    /**
     * 获取所有的试卷
     * @return Result
     */
    @GetMapping("/p")
    fun allPaper(): Result {
        val allPapers = paperService.getAllPapers()
        return ResultUtils.success(data = allPapers)
    }

    /**
     * 教师获取单张试卷
     * @param pageId ") pageId: Long
     * @return Result
     */
    @GetMapping("/p/{pageId}")
    fun getOnePaper(@PathVariable("pageId") pageId: Long): Result {
        val pageInfo = paperService.getOnePaper(pageId)
        return ResultUtils.success(data = pageInfo)
    }


    /**
     * 提交详细的试卷班级安排
     * @param pageClassParam PageClassParam
     * @return Result
     */
    @PostMapping("/class")
    fun sendPageAndClass(@RequestBody pageClassParam: PageClassParam): Result {
        paperService.savePageAndClass(pageClassParam)
        return ResultUtils.success()
    }
}
