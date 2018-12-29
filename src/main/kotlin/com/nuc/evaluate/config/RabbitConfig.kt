package com.nuc.evaluate.config

import org.springframework.amqp.core.Queue
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
     * 设置一个消息队列
     */
    @Bean
    fun checkAns(): Queue {
        return Queue("check")
    }

}