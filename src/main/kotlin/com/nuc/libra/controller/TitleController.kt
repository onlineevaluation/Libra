package com.nuc.libra.controller

import com.nuc.libra.result.Result
import com.nuc.libra.service.TitleService
import com.nuc.libra.util.ResultUtils
import com.nuc.libra.vo.WrongTitleParam
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

/**
 * @author 杨晓辉 2019/3/4 19:52
 */
@RestController
@RequestMapping("/title")
class TitleController {

    @Autowired
    private lateinit var titleService: TitleService


    /**
     * 提交判题失误
     * @param wrongTitleParam 错误试题
     */
    @PostMapping("/problem")
    fun feedbackTitleProblem(wrongTitleParam: WrongTitleParam): Result {

        titleService.addWrongTitle(wrongTitleParam)
        return ResultUtils.success()

    }


    /**
     * 根据试题类型获取所有相关试题
     * @param typeIds  Array<String>
     * @return Result
     */
    @GetMapping("/titles/{typeIds}/{courseId}")
    fun getTitleList(@PathVariable("typeIds") typeIds: Array<String>,@PathVariable("courseId") courseId:Long): Result {
        val list = titleService.getTitleByTypes(typeIds,courseId)
        return ResultUtils.success(data = list)
    }
}