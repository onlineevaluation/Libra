package com.nuc.libra.controller

import com.nuc.libra.result.Result
import com.nuc.libra.util.ResultUtils
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * @author 杨晓辉 2018/2/1 11:20
 */
@RestController
class IndexController {

    /**
     * 首页
     */
    @RequestMapping("/")
    fun index(): Result {
        return ResultUtils.success(message = "swagger-ui.html")
    }

}