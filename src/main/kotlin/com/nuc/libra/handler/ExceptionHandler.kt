package com.nuc.libra.handler

import com.nuc.libra.exception.ResultException
import com.nuc.libra.result.Result
import com.nuc.libra.util.ResultUtils
import io.jsonwebtoken.ExpiredJwtException
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
class ExceptionHandler {

    private final val logger: Logger = LoggerFactory.getLogger(ExceptionHandler::class.java)

    /**
     * 结果异常捕获
     * @param e 异常类
     *
     */
    @ResponseBody
    @ExceptionHandler(value = [(Exception::class)])
    fun handle(e: Exception): Result {
        return when (e) {
            is ResultException -> {
                logger.info("发生了异常 $e")
                val resultException: ResultException = e
                ResultUtils.error(resultException.code, resultException.message!!)
            }
            is ExpiredJwtException -> {
                logger.info("token失效")

                ResultUtils.error(501, e.message.toString())}
            else -> {
                logger.error("[系统异常]", e)
                ResultUtils.error(-1, "未知错误")
            }
        }
    }

}