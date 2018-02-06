package com.nuc.evaluate.config

import org.springframework.amqp.core.Queue
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

/**
 * @author 杨晓辉 2018/2/6 16:07
 */
@Configuration
class RabbitConfig {

    @Bean
    fun queuePage(): Queue {
        return Queue("page")
    }

}