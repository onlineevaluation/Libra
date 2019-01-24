package com.nuc.libra.config

import org.springframework.boot.web.servlet.ServletContextInitializer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import io.sentry.Sentry
import io.sentry.spring.SentryExceptionResolver
import io.sentry.spring.SentryServletContextInitializer
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.web.servlet.HandlerExceptionResolver


/**
 * @author 杨晓辉 2019-01-24 9:22
 */
@Configuration
class SentryConfig {

    private val logger = LoggerFactory.getLogger(this::class.java)

    @Value("\${sentry.dsn}")
    private lateinit var sentryDSN: String


    /**
     * Sentry 日志收集初始化
     */
    @Bean
    fun sentryServletContextInitializer(): ServletContextInitializer {
        return SentryServletContextInitializer()
    }


    /**
     * 配置日志收集
     */
    @Bean
    fun sentryExceptionResolver(): HandlerExceptionResolver {
        if (!sentryDSN.isEmpty()) {
            Sentry.init(sentryDSN)
        } else {
            logger.error("sentry dsn 没有进行配置.")
        }
        return SentryExceptionResolver()
    }
}