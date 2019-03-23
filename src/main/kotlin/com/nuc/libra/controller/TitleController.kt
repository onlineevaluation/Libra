package com.nuc.libra.controller

import com.nuc.libra.result.Result
import com.nuc.libra.util.ResultUtils
import com.nuc.libra.vo.WorngTitlesParam
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * @author 杨晓辉 2019/3/4 19:52
 */
@RestController
@RequestMapping("/title")
class TitleController {

    /**
     * 提交判题失误
     */
    @PostMapping("/problem")
    fun feedbackTitleProblem(problem: WorngTitlesParam): Result {


        return ResultUtils.success()
    }
}