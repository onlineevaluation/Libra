package com.nuc.evaluate.handle

import com.nuc.evaluate.entity.EmailMessage
import com.nuc.evaluate.exception.ResultException
import com.nuc.evaluate.result.Result
import com.nuc.evaluate.service.MailService
import com.nuc.evaluate.util.ResultUtils
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import java.io.ByteArrayOutputStream
import java.io.PrintWriter
import java.text.SimpleDateFormat
import java.util.*

/**
 * @author 杨晓辉 2018/2/1 14:04
 * 异常捕获类
 */
@ControllerAdvice
class ExceptionHandle {

    private final val logger: Logger = LoggerFactory.getLogger(ExceptionHandle::class.java)

    @Autowired
    lateinit var mailService: MailService


    @ResponseBody
    @ExceptionHandler(value = [(Exception::class)])
    fun handle(e: Exception): Result {
        return if (e is ResultException) {
            val resultException: ResultException = e
            ResultUtils.error(resultException.code!!, resultException.message!!)
        } else {
            logger.error("[系统异常]", e)
            val to: Array<String> = arrayOf("youngxhui@qq.com")
            val subject = "[在线教育平台]-异常通知"
            val emailMessage = EmailMessage()


            val buf = ByteArrayOutputStream()
            e.printStackTrace(PrintWriter(buf, true))
            val expMessage = buf.toString()
            buf.close()
            emailMessage.exception = expMessage
            emailMessage.time = SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Date())
            mailService.sendInlineMail(to, subject, emailMessage, "ExceptionMail.ftl")
            ResultUtils.error(-1, "未知错误")
        }
    }


}