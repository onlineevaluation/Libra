package com.nuc.libra.config

import org.springframework.amqp.core.Queue
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

/**
 * @author 杨晓辉 2018/2/6 16:07
 */
/**
 * RabbitMQ 配置类
 */
@Configuration
class RabbitConfig {

    /**
     * 正式队列
     */
    @Bean
    fun checkAns(): Queue {
        return Queue("check")
    }

    /**
     * 测试队列
     */
    @Bean
    fun testMQ():Queue {
        return Queue("test")
    }



}