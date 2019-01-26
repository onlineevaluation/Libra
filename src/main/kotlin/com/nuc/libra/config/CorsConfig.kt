package com.nuc.libra.config

import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

/**
 * @author 杨晓辉 2018/2/1 11:41
 * 全局跨域请求支持
 */
@Configuration
class CorsConfig : WebMvcConfigurer {

    /**
     * 跨域请求配置
     * @param registry 跨域请求
     */
    override fun addCorsMappings(registry: CorsRegistry) {
        registry.addMapping("/**")
            .allowedOrigins("*")
            .allowedMethods("GET", "HEAD", "POST", "PUT", "DELETE", "OPTIONS")
            .allowCredentials(false)
            .maxAge(3600)
    }
}