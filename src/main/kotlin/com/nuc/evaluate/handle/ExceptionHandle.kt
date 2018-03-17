package com.nuc.evaluate.handle

import com.nuc.evaluate.exception.ResultException
import com.nuc.evaluate.result.Result
import com.nuc.evaluate.util.ResultUtils
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody

/**
 * @author 杨晓辉 2018/2/1 14:04
 * 异常捕获类
 */
@ControllerAdvice
class ExceptionHandle {

    private final val logger: Logger = LoggerFactory.getLogger(ExceptionHandle::class.java)


    @ResponseBody
    @ExceptionHandler(value = [(Exception::class)])
    fun handle(e: Exception): Result {
        return if (e is ResultException) {
            val resultException: ResultException = e
            ResultUtils.error(resultException.code!!, resultException.message!!)
        } else {
            logger.error("[系统异常]", e)
//            sendEmail(e)
            ResultUtils.error(-1, "未知错误")
        }
    }

}