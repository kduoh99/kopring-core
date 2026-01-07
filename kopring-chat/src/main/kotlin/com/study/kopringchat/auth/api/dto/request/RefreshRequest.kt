package com.study.kopringchat.auth.api.dto.request

import jakarta.validation.constraints.NotBlank

data class RefreshRequest(
    @field:NotBlank(message = "리프레시 토큰은 필수 입력 항목입니다.")
    val refreshToken: String
)
