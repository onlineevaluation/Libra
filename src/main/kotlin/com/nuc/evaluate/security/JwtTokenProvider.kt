package com.nuc.evaluate.security

import com.nuc.evaluate.exception.ResultException
import com.nuc.evaluate.service.impl.UserServiceImpl
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Component
import java.util.*
import javax.annotation.PostConstruct
import javax.servlet.http.HttpServletRequest

/**
 * @author 杨晓辉
 * Token生成
 */
@Component
class JwtTokenProvider {

    /**
     * token加密密钥
     */
    @Value("\${jwt.token.secret}")
    private lateinit var secretKey: String

    /**
     * 过期时间
     */
    @Value("\${jwt.token.expire}")
    private val validityInMilliseconds: Long = 3600000 // 1 h

    @Autowired
    private lateinit var userDetailsService: UserServiceImpl

    /**
     * Base64 编码密钥
     */
    @PostConstruct
    protected fun init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.toByteArray())
    }

    /**
     * 创建token 使用 HS256加密
     * @param username 用户学号
     * @param role 角色信息
     * @return jwtToken
     */
    fun createToken(username: String, role: String): String {
        val claims = Jwts.claims().setSubject(username)
        claims["auth"] = role
        val now = Date()
        val validity = Date(now.time + validityInMilliseconds)
        return Jwts.builder()
            .setClaims(claims)
            .setIssuedAt(now)
            .setExpiration(validity)
            .signWith(SignatureAlgorithm.HS256, secretKey)
            .compact()
    }

    internal fun getAuthentication(token: String): Authentication {
        val userDetails = userDetailsService.loadUserByUsername(getUsername(token))
        return UsernamePasswordAuthenticationToken(userDetails, "", userDetails.authorities)
    }

    /**
     * 获取token
     * @param token token信息
     *
     */
    fun getUsername(token: String): String {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).body.subject
    }

    /**
     * 解析token
     * 前端 header头信息用 Authorization
     * Token用 Nuc 拼接 **Nuc后面有个空格**
     * @simple Authorization Nuc xxx.yyy.zzz
     */
    fun resolveToken(req: HttpServletRequest): String? {
        val bearerToken = req.getHeader("Authorization")
        return if (bearerToken != null && bearerToken.startsWith("Nuc ")) {
            bearerToken.substring(4, bearerToken.length)
        } else null
    }

    /**
     * token验证
     * @param token token
     * @return boolean 返回token是否有效
     */
    fun validateToken(token: String): Boolean {
        Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token) ?: throw ResultException("无效的token", 500)
        return true
    }

}
