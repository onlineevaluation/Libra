package com.nuc.libra.config

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.client.RestTemplate

/**
 * @author 杨晓辉 2019/4/15 20:04
 */
@Configuration
class RestTemplateConfig {

    @Autowired
    lateinit var builder: RestTemplateBuilder

    @Bean
    fun restTemplate(): RestTemplate {
        return builder.build();
    }
}