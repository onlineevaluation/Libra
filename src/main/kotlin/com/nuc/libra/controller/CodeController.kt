package com.nuc.libra.controller

import com.nuc.libra.vo.Code
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * @author 杨晓辉 2019/2/28 9:57
 */
@RestController
@RequestMapping("/code")
class CodeController {

    private val logger: Logger = LoggerFactory.getLogger(this.javaClass)


    // todo 命名规范问题
    @PostMapping("/submit")
    fun submitCode(@RequestBody codeArray: Array<Code>) {
        logger.info("submit code ${codeArray.size}")
    }

}