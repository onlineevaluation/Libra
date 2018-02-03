package com.nuc.evaluate.controller

import com.nuc.evaluate.result.Result
import com.nuc.evaluate.util.ResultUtils
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * @author 杨晓辉 2018/2/1 11:20
 */
@RestController
class IndexController {

    @RequestMapping("/")
    fun index(): Result = ResultUtils.success()
}