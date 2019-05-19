package com.nuc.libra.config

import com.nuc.libra.result.Result
import com.nuc.libra.util.ResultUtils
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * @author 杨晓辉 2019/5/19 19:48
 */
@RestController
@RequestMapping("/logs")
class LogsController {

    /**
     * 提交所有的教师相关日志
     */
    @PostMapping("/teachers")
    fun addLogsAboutTeacher(): Result {


        return ResultUtils.success()
    }

}