package com.study.kopringchat.auth.api.dto.request

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank

data class LoginRequest(
    @field:NotBlank(message = "이메일은 필수 입력 항목입니다.")
    @field:Email(message = "유효하지 않은 이메일 형식입니다.")
    val email: String,

    @field:NotBlank(message = "비밀번호는 필수 입력 항목입니다.")
    val password: String
)
