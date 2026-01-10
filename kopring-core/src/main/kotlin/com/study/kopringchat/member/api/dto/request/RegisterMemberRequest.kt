package com.study.kopringchat.member.api.dto.request

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class RegisterMemberRequest(
    @field:NotBlank(message = "이메일은 필수 입력 항목입니다.")
    @field:Email(message = "유효하지 않은 이메일 형식입니다.")
    val email: String,

    @field:NotBlank(message = "이름은 필수 입력 항목입니다.")
    @field:Size(min = 2, max = 20, message = "이름은 2자 이상 20자 이하로 입력해 주세요.")
    val name: String,

    @field:NotBlank(message = "비밀번호는 필수 입력 항목입니다.")
    @field:Size(min = 8, message = "비밀번호는 최소 8자 이상이어야 합니다.")
    val password: String
)
