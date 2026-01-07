package com.study.kopringchat.member.api.dto.response

import com.study.kopringchat.member.domain.Member

data class FindMembersResponse(
    val memberId: Long,
    val name: String,
    val email: String
)

fun Member.toFindMembersResponse() = FindMembersResponse(
    memberId = this.id!!,
    name = this.name,
    email = this.email
)
