package com.nuc.libra.controller

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * @author 杨晓辉 2019/3/4 19:52
 */
@RestController
@RequestMapping("/title")
class TitleController {

    @PostMapping("/problem")
    fun feedbackTitleProblem(){

    }
}