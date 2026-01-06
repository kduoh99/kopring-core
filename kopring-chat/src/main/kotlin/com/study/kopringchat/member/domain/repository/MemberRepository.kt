package com.study.kopringchat.member.domain.repository

import com.study.kopringchat.member.domain.Member
import org.springframework.data.jpa.repository.JpaRepository

interface MemberRepository : JpaRepository<Member, Long> {
}
