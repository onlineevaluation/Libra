package com.nuc.libra.security

import org.springframework.security.config.annotation.SecurityConfigurerAdapter
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.web.DefaultSecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

/**
 * @author 杨晓辉
 * 拦截器配置
 */
class JwtTokenFilterConfigurer(private val jwtTokenProvider: JwtTokenProvider) :
    SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity>() {

    /**
     * 拦截器配置获取
     */
    @Throws(Exception::class)
    override fun configure(http: HttpSecurity) {
        val customFilter = JwtTokenFilter(jwtTokenProvider)
        http.addFilterBefore(customFilter, UsernamePasswordAuthenticationFilter::class.java)
    }

}
