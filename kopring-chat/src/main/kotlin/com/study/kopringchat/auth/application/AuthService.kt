package com.study.kopringchat.auth.application

import com.study.kopringchat.auth.api.dto.request.LoginRequest
import com.study.kopringchat.auth.api.dto.request.RefreshRequest
import com.study.kopringchat.auth.exception.AuthErrorCode
import com.study.kopringchat.global.security.exception.JwtErrorCode
import com.study.kopringchat.global.security.jwt.JwtInfo
import com.study.kopringchat.global.security.jwt.JwtUtils
import com.study.kopringchat.global.exception.CustomException
import com.study.kopringchat.member.domain.repository.MemberRepository
import com.study.kopringchat.member.exception.MemberErrorCode
import org.springframework.beans.factory.annotation.Value
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.Instant
import java.util.concurrent.TimeUnit

@Service
@Transactional(readOnly = true)
class AuthService(
    private val memberRepository: MemberRepository,
    private val passwordEncoder: PasswordEncoder,
    private val jwtUtils: JwtUtils,
    private val redisTemplate: StringRedisTemplate,

    @Value("\${jwt.expire.refresh}")
    private val refreshTokenExpire: Long
) {
    fun login(request: LoginRequest): JwtInfo {
        val member = memberRepository.findByEmail(request.email)
            ?: throw CustomException(AuthErrorCode.AUTH_LOGIN_FAILED)

        if (!passwordEncoder.matches(request.password, member.password)) {
            throw CustomException(AuthErrorCode.AUTH_LOGIN_FAILED)
        }

        val jwtInfo = jwtUtils.generate(member)

        redisTemplate.opsForValue().set(
            "refreshToken:${member.email}",
            jwtUtils.getToken(jwtInfo.refreshToken),
            refreshTokenExpire,
            TimeUnit.MILLISECONDS
        )

        return jwtInfo
    }

    fun logout(accessToken: String) {
        val token = jwtUtils.getToken(accessToken)

        val email = jwtUtils.getUsername(token)
        redisTemplate.delete("refreshToken:$email")

        val expiration = jwtUtils.getExpiration(token)
        val remainTime = expiration - Instant.now().toEpochMilli()

        if (remainTime > 0) {
            redisTemplate.opsForValue().set(
                "blacklist:$token",
                "logout",
                remainTime,
                TimeUnit.MILLISECONDS
            )
        }
    }

    fun refreshToken(request: RefreshRequest): String {
        val token = jwtUtils.getToken(request.refreshToken)
        jwtUtils.checkTokenInfo(token)

        if (!jwtUtils.isRefreshToken(token)) {
            throw CustomException(JwtErrorCode.JWT_INVALID_TYPE)
        }

        val email = jwtUtils.getUsername(token)

        val savedToken = redisTemplate.opsForValue().get("refreshToken:$email")
        if (savedToken == null || savedToken != token) {
            throw CustomException(JwtErrorCode.JWT_INVALID)
        }

        val member = memberRepository.findByEmail(email)
            ?: throw CustomException(MemberErrorCode.MEMBER_NOT_FOUND)

        return jwtUtils.refreshAccessToken(member)
    }
}
