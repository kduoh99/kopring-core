package com.study.kopringchat.auth.api

import com.study.kopringchat.auth.api.dto.request.LoginRequest
import com.study.kopringchat.auth.api.dto.request.RefreshRequest
import com.study.kopringchat.auth.application.AuthService
import com.study.kopringchat.global.auth.jwt.JwtInfo
import com.study.kopringchat.global.common.BaseResponse
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/auth")
class AuthController(
    private val authService: AuthService
) : AuthDocs {

    @PostMapping("/login")
    override fun login(@Valid @RequestBody request: LoginRequest): BaseResponse<JwtInfo> {
        return BaseResponse(
            status = HttpStatus.OK,
            message = "로그인 성공",
            data = authService.login(request)
        )
    }

    @PostMapping("/logout")
    override fun logout(@RequestHeader("Authorization") accessToken: String): BaseResponse<Unit> {
        authService.logout(accessToken)

        return BaseResponse(
            status = HttpStatus.OK,
            message = "로그아웃 성공"
        )
    }

    @PostMapping("/refresh")
    override fun refresh(@Valid @RequestBody request: RefreshRequest): BaseResponse<String> {
        return BaseResponse(
            status = HttpStatus.OK,
            message = "토큰 재발급 성공",
            data = authService.refreshToken(request)
        )
    }
}
