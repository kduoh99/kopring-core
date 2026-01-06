package com.study.kopringchat.global.auth.jwt

import com.study.kopringchat.global.exception.CustomException
import com.study.kopringchat.member.domain.repository.MemberRepository
import com.study.kopringchat.member.exception.MemberErrorCode
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class JwtUserDetailsService(
    private val memberRepository: MemberRepository
) : UserDetailsService {
    override fun loadUserByUsername(email: String): UserDetails {
        val member = memberRepository.findByEmail(email)
            ?: throw CustomException(MemberErrorCode.MEMBER_NOT_FOUND)
        return JwtUserDetails(member)
    }
}
