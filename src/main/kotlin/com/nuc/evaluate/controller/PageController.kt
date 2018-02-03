package com.nuc.evaluate.controller


import com.nuc.evaluate.result.Result
import com.nuc.evaluate.service.PaperService
import com.nuc.evaluate.util.ResultUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * @author 杨晓辉 2018/2/3 11:22
 */
@RestController
@RequestMapping("/page")
class PageController {

    @Autowired
    private lateinit var paperService: PaperService

    @GetMapping("/listPages")
    fun listPages(classId: Long): Result {
        return ResultUtils.success(200, "查询成功", paperService.getOneClassPaper(classId))
    }

    @GetMapping("/onePage")
    fun getPage(pageId: Long): Result {

        return ResultUtils.success()
    }

}