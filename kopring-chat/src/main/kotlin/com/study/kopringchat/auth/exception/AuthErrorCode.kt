package com.study.kopringchat.auth.exception

import com.study.kopringchat.global.exception.CustomErrorCode
import org.springframework.http.HttpStatus

enum class AuthErrorCode(
    override val status: HttpStatus,
    override val state: String,
    override val message: String,
) : CustomErrorCode {
    // 400 Bad Request
    AUTH_LOGIN_FAILED(HttpStatus.BAD_REQUEST, "AUTH_LOGIN_FAILED", "이메일 또는 비밀번호가 일치하지 않습니다.")
}
