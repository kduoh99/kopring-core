package com.study.kopringchat.global.security.jwt

import com.study.kopringchat.global.security.exception.JwtErrorCode
import com.study.kopringchat.global.exception.CustomException
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class JwtAuthenticationFilter(
    private val jwtUtils: JwtUtils,
    private val redisTemplate: StringRedisTemplate
) : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val authorizationHeader: String? = request.getHeader("Authorization")

        if (!authorizationHeader.isNullOrBlank()) {
            val token = jwtUtils.getToken(authorizationHeader)

            if (redisTemplate.hasKey("blacklist:$token")) {
                throw CustomException(JwtErrorCode.JWT_INVALID)
            }

            jwtUtils.checkTokenInfo(token)
            SecurityContextHolder.getContext().authentication = jwtUtils.getAuthentication(token)
        }

        filterChain.doFilter(request, response)
    }
}
