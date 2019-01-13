package com.nuc.evaluate.security

import com.nuc.evaluate.exception.ResultException
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.GenericFilterBean
import java.io.IOException
import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * @author 杨晓辉
 * Jwt token 的拦截器
 */
class JwtTokenFilter(private val jwtTokenProvider: JwtTokenProvider) : GenericFilterBean() {

//    private final val logger: Logger = LoggerFactory.getLogger(this.javaClass)

    /**
     * 拦截方法，将token进行拦截获取
     */
    @Throws(IOException::class, ServletException::class)
    override fun doFilter(req: ServletRequest, res: ServletResponse, filterChain: FilterChain) {

        val token = jwtTokenProvider.resolveToken(req as HttpServletRequest)
        try {
            if (token != null && jwtTokenProvider.validateToken(token)) {
                val auth = if (token != null) jwtTokenProvider.getAuthentication(token) else null
                SecurityContextHolder.getContext().authentication = auth
            }
        } catch (ex: ResultException) {
            val response = res as HttpServletResponse
            println(response)
            return
        }
        filterChain.doFilter(req, res)
    }
}
