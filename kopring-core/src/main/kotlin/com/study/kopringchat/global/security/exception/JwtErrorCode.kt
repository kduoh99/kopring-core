package com.study.kopringchat.global.security.exception

import com.study.kopringchat.global.exception.CustomErrorCode
import org.springframework.http.HttpStatus

enum class JwtErrorCode(
    override val status: HttpStatus,
    override val state: String,
    override val message: String,
) : CustomErrorCode {
    // 400 Bad Request
    JWT_INVALID_TYPE(HttpStatus.BAD_REQUEST, "JWT_INVALID_TYPE", "잘못된 토큰 타입입니다."),

    // 401 Unauthorized
    JWT_MISSING(HttpStatus.UNAUTHORIZED, "JWT_MISSING", "토큰이 존재하지 않습니다."),
    JWT_EXPIRED(HttpStatus.UNAUTHORIZED, "JWT_EXPIRED", "토큰이 만료되었습니다."),
    JWT_INVALID_SIGNATURE(HttpStatus.UNAUTHORIZED, "JWT_INVALID_SIGNATURE", "서명이 일치하지 않습니다."),
    JWT_INVALID(HttpStatus.UNAUTHORIZED, "JWT_INVALID", "유효하지 않은 토큰입니다."),
    JWT_UNSUPPORTED(HttpStatus.UNAUTHORIZED, "JWT_UNSUPPORTED", "지원하지 않는 토큰입니다."),
    JWT_MALFORMED(HttpStatus.UNAUTHORIZED, "JWT_MALFORMED", "토큰 형식이 잘못되었습니다."),
    JWT_UNKNOWN_ERROR(HttpStatus.UNAUTHORIZED, "JWT_UNKNOWN_ERROR", "알 수 없는 인증 오류입니다."),

    // 403 Forbidden
    JWT_ACCESS_DENIED(HttpStatus.FORBIDDEN, "JWT_ACCESS_DENIED", "접근 권한이 없습니다.")
}
