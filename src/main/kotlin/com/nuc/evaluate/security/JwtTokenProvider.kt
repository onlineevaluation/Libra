package com.nuc.evaluate.security

import com.nuc.evaluate.exception.ResultException
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

@Component
class JwtTokenProvider {

    @Value("\${jwt.token.secret}")
    private var secretKey: String? = null

    @Value("\${jwt.token.expire}")
    private val validityInMilliseconds: Long = 3600000 // 1h

    @Autowired
    private val myUserDetails: MyUserDetails? = null

    @PostConstruct
    protected fun init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey!!.toByteArray())
    }

    fun createToken(username: String, role: String): String {

        val claims = Jwts.claims().setSubject(username)
        claims["auth"] = role

        val now = Date()
        val validity = Date(now.time + validityInMilliseconds)

        return Jwts.builder()//
            .setClaims(claims)//
            .setIssuedAt(now)//
            .setExpiration(validity)//
            .signWith(SignatureAlgorithm.HS256, secretKey)//
            .compact()
    }

    internal fun getAuthentication(token: String): Authentication {
        val userDetails = myUserDetails!!.loadUserByUsername(getUsername(token))
        return UsernamePasswordAuthenticationToken(userDetails, "", userDetails.authorities)
    }

    fun getUsername(token: String): String {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).body.subject
    }

    fun resolveToken(req: HttpServletRequest): String? {
        val bearerToken = req.getHeader("Authorization")
        return if (bearerToken != null && bearerToken.startsWith("Nuc ")) {
            bearerToken.substring(4, bearerToken.length)
        } else null
    }

    fun validateToken(token: String): Boolean {
        Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token) ?: throw ResultException("无效的token", 500)
        return true
    }

}
