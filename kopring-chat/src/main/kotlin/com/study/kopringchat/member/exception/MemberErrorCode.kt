package com.study.kopringchat.member.exception

import com.study.kopringchat.global.exception.CustomErrorCode
import org.springframework.http.HttpStatus

enum class MemberErrorCode (
    override val status: HttpStatus,
    override val state: String,
    override val message: String,
) : CustomErrorCode {
    // 400 Bad Request
    MEMBER_NOT_MATCH(HttpStatus.BAD_REQUEST, "MEMBER_NOT_MATCH", "이메일 또는 비밀번호가 일치하지 않습니다."),

    // 404 Not Found
    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "MEMBER_NOT_FOUND", "존재하지 않는 회원입니다."),

    // 409 Conflict
    MEMBER_ALREADY_EXIST(HttpStatus.CONFLICT, "MEMBER_ALREADY_EXIST", "이미 존재하는 회원입니다.")
}
