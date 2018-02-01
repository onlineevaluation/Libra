package com.nuc.evaluate.handle

import com.nuc.evaluate.exception.ResultException
import com.nuc.evaluate.result.Result
import com.nuc.evaluate.util.ResultUtils
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.ControllerAdvice

/**
 * @author 杨晓辉 2018/2/1 14:04
 */
@ControllerAdvice
class ExceptionHandle {

    private final val logger: Logger = LoggerFactory.getLogger(ExceptionHandle::class.java)

    fun handle(e: Exception): Result {
        if (e is ResultException) {
            val resultException: ResultException = e
            return ResultUtils.error(resultException.code!!, resultException.message!!)
        } else {
            logger.error("[系统异常]", e)
            //Todo(添加异常邮件发送)
            return ResultUtils.error(-1, "位置错误")
        }
    }


}