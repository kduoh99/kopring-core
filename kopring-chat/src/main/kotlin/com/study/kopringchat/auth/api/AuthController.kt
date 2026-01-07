package com.study.kopringchat.auth.api

import com.study.kopringchat.auth.api.dto.request.LoginRequest
import com.study.kopringchat.auth.application.AuthService
import com.study.kopringchat.global.auth.jwt.JwtInfo
import com.study.kopringchat.global.common.BaseResponse
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/auth")
class AuthController(
    private val authService: AuthService
) : AuthDocs {

    @PostMapping("/login")
    override fun login(@Valid @RequestBody request: LoginRequest): BaseResponse<JwtInfo> {
        val jwtInfo = authService.login(request)

        return BaseResponse(
            status = HttpStatus.OK,
            message = "로그인 성공",
            data = jwtInfo
        )
    }
}
