package com.study.kopringchat.auth.api

import com.study.kopringchat.auth.api.dto.request.LoginRequest
import com.study.kopringchat.global.auth.jwt.JwtInfo
import com.study.kopringchat.global.common.BaseResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.ExampleObject
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.parameters.RequestBody
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag

@Tag(name = "Auth", description = "인증 관련 API")
interface AuthDocs {

    @Operation(
        summary = "로그인",
        description = "이메일과 비밀번호로 로그인을 진행합니다."
    )
    @ApiResponse(
        responseCode = "200",
        description = "로그인 성공",
        content = [Content(
            mediaType = "application/json",
            schema = Schema(implementation = BaseResponse::class),
            examples = [ExampleObject(
                name = "로그인 성공 응답 예시",
                value = """
                {
                    "status": 200,
                    "message": "로그인 성공",
                    "data": {
                        "accessToken": "Bearer ey...",
                        "refreshToken": "Bearer ey..."
                    }
                }
                """
            )]
        )]
    )
    fun login(
        @RequestBody(
            required = true,
            description = "로그인 요청 DTO",
            content = [Content(
                mediaType = "application/json",
                schema = Schema(implementation = LoginRequest::class),
                examples = [ExampleObject(
                    name = "로그인 요청 예시",
                    value = """
                    {
                      "email": "test@example.com",
                      "password": "password123!"
                    }
                    """
                )]
            )]
        )
        request: LoginRequest
    ): BaseResponse<JwtInfo>
}
