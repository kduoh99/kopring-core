package com.study.kopringchat.member.application

import com.study.kopringchat.global.exception.CustomException
import com.study.kopringchat.member.api.dto.request.RegisterMemberRequest
import com.study.kopringchat.member.api.dto.response.FindMembersResponse
import com.study.kopringchat.member.api.dto.response.toFindMembersResponse
import com.study.kopringchat.member.domain.Member
import com.study.kopringchat.member.domain.repository.MemberRepository
import com.study.kopringchat.member.exception.MemberErrorCode
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class MemberService(
    private val memberRepository: MemberRepository,
    private val passwordEncoder: PasswordEncoder
) {
    @Transactional
    fun registerMember(request: RegisterMemberRequest): Long {
        if (memberRepository.existsByEmail(request.email)) {
            throw CustomException(MemberErrorCode.MEMBER_ALREADY_EXIST)
        }

        val member = Member(
            email = request.email,
            name = request.name,
            password = passwordEncoder.encode(request.password)!!
        )

        return memberRepository.save(member).id!!
    }

    fun findMembers(): List<FindMembersResponse> {
        return memberRepository.findAll()
            .map { it.toFindMembersResponse() }
    }
}
