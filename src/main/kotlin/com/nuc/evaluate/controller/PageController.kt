package com.nuc.evaluate.controller


import com.nuc.evaluate.po.Pages
import com.nuc.evaluate.po.User
import com.nuc.evaluate.result.Result
import com.nuc.evaluate.util.ResultUtils
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * @author 杨晓辉 2018/2/3 11:22
 */
@RestController
@RequestMapping("/page")
class PageController {


    @GetMapping("/listPages")
    fun listPages(user: User): Result {

        return ResultUtils.success()
    }

}