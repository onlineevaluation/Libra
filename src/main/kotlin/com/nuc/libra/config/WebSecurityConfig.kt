package com.nuc.libra.config

import com.nuc.libra.security.JwtTokenFilterConfigurer
import com.nuc.libra.security.JwtTokenProvider
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder

/**
 * @author young
 * WebSecurity 的配置
 */
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
class WebSecurityConfig : WebSecurityConfigurerAdapter() {

    @Autowired
    private lateinit var jwtTokenProvider: JwtTokenProvider

    /**
     * 拦截请求配置
     * @param http 要拦截的请求
     */
    @Throws(Exception::class)
    override fun configure(http: HttpSecurity) {

        http.csrf().disable()
        // 跨域配置
        http.cors()
        // session 配置
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)

        // 权限配置
        http.authorizeRequests()
            .antMatchers("/druid/**").permitAll()
            .antMatchers("/user/login").permitAll()
            .antMatchers("/").permitAll()
            .anyRequest().authenticated()


        // 使用 jwt
        http.apply(JwtTokenFilterConfigurer(jwtTokenProvider))

    }

    /**
     * 忽略配置
     * @param web 忽略配置
     */
    @Throws(Exception::class)
    override fun configure(web: WebSecurity) {
        // Allow swagger to be accessed without authentication
        web.ignoring().antMatchers("/v2/api-docs")
            .antMatchers("/swagger-resources/**")
            .antMatchers("/swagger-ui.html")
            .antMatchers("/configuration/**")
            .antMatchers("/webjars/**")
            .antMatchers("/public")
            .antMatchers("/druid/**")
    }

    /**
     * 密码加密方式
     */
    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

    /**
     * 重新该方法进行注入
     */
    @Bean
    @Throws(Exception::class)
    override fun authenticationManagerBean(): AuthenticationManager {
        return super.authenticationManagerBean()
    }


}
