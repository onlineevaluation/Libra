package com.nuc.evaluate.config

import org.springframework.amqp.core.BindingBuilder
import org.springframework.amqp.core.FanoutExchange
import org.springframework.amqp.core.Queue
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

/**
 * @author 杨晓辉 2018/2/6 16:07
 */
@Configuration
class RabbitConfig {

    @Bean
    fun checkAns(): Queue {
        return Queue("fanout.check")
    }

    @Bean
    fun addAns(): Queue {
        return Queue("fanout.add")
    }

    @Bean
    fun fanoutExchange(): FanoutExchange = FanoutExchange("fanoutExchange")


    @Bean
    fun bindingExchangeCheck(checkAns: Queue, fanoutExchange: FanoutExchange)
            = BindingBuilder.bind(checkAns).to(fanoutExchange)!!

    @Bean
    fun bindingExchangeAdd(addAns: Queue, fanoutExchange: FanoutExchange)
            = BindingBuilder.bind(addAns).to(fanoutExchange)!!

}