package com.study.kopringchat.auth.application

import com.study.kopringchat.auth.api.dto.request.LoginRequest
import com.study.kopringchat.auth.exception.AuthErrorCode
import com.study.kopringchat.global.auth.jwt.JwtInfo
import com.study.kopringchat.global.auth.jwt.JwtUtils
import com.study.kopringchat.global.exception.CustomException
import com.study.kopringchat.member.domain.repository.MemberRepository
import org.springframework.beans.factory.annotation.Value
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
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
            jwtInfo.refreshToken,
            refreshTokenExpire,
            TimeUnit.MILLISECONDS
        )

        return jwtInfo
    }
}
