package com.nuc.libra.handler

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.amqp.AmqpRejectAndDontRequeueException
import org.springframework.amqp.rabbit.listener.FatalExceptionStrategy
import org.springframework.util.ErrorHandler
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody


/**
 * @author 杨晓辉 2018-03-13 8:37
 */
@ControllerAdvice
class ConditionalRejectingErrorHandler : ErrorHandler {
    private val logger: Logger = LoggerFactory.getLogger(this.javaClass)


    private final lateinit var exceptionStrategy: FatalExceptionStrategy

    /**
     * 获取 RabbitMQ 的异常
     */
    @ResponseBody
    @ExceptionHandler(value = [(Exception::class)])
    override fun handleError(t: Throwable) {
        if (this.logger.isWarnEnabled) {
            this.logger.warn("Execution of Rabbit message listener failed.", t)
        }
        if (!this.causeChainContainsARADRE(t) && this.exceptionStrategy.isFatal(t)) {
            throw AmqpRejectAndDontRequeueException("Error Handler converted exception to fatal", t)
        }

    }

    private fun causeChainContainsARADRE(t: Throwable): Boolean {
        var cause = t.cause
        while (cause != null) {
            if (cause is AmqpRejectAndDontRequeueException) {
                return true
            }
            cause = cause.cause
        }
        return false
    }
}