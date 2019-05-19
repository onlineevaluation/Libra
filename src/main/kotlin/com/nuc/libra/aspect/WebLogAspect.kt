package com.nuc.libra.aspect

import org.aspectj.lang.JoinPoint
import org.aspectj.lang.annotation.AfterReturning
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Before
import org.aspectj.lang.annotation.Pointcut
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.request.ServletRequestAttributes
import java.util.*

/**
 * @author 杨晓辉 2019/5/19 20:03
 */
@Aspect
@Component
class WebLogAspect {

    private val logger: Logger = LoggerFactory.getLogger(this.javaClass)

    @Pointcut("execution(public * com.nuc.libra.controller..*.*(..))")
    fun webLog() {
    }

    @Before("webLog()")
    fun doBefore(joinPoint: JoinPoint) {
        val attributes = RequestContextHolder.getRequestAttributes() as ServletRequestAttributes
        val request = attributes.request
        logger.info("URL == ${request.requestURI},HTTP_METHOD == ${request.method},IP == ${request.remoteAddr}," +
                "CLASS_METHOD == ${joinPoint.signature.declaringTypeName}.${joinPoint.signature.name}," +
                "ARGS == ${Arrays.toString(joinPoint.args)}")

    }

    @AfterReturning(returning = "ret", pointcut = "webLog()")
    fun doAfterReturning(ret: Any) {
        logger.info("RESPONSE == $ret")
    }
}