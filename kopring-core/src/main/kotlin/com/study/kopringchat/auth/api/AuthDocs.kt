package com.study.kopringchat.auth.api

import com.study.kopringchat.auth.api.dto.request.LoginRequest
import com.study.kopringchat.auth.api.dto.request.RefreshRequest
import com.study.kopringchat.global.security.jwt.JwtInfo
import com.study.kopringchat.global.common.BaseResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.ExampleObject
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.parameters.RequestBody
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag

@Tag(name = "Auth", description = "인증 관련 API")
interface AuthDocs {

    @Operation(summary = "로그인", security = [], description = "이메일과 비밀번호로 로그인을 진행합니다.")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200", description = "로그인 성공", content = [Content(
                    schema = Schema(implementation = BaseResponse::class), examples = [
                        ExampleObject(
                            name = "로그인 성공 예시",
                            value = """{ "status": 200, "message": "로그인 성공", "data": { "accessToken": "Bearer ey...", "refreshToken": "Bearer ey..." } }"""
                        )
                    ]
                )]
            ),
            ApiResponse(
                responseCode = "400", description = "잘못된 요청", content = [Content(
                    examples = [
                        ExampleObject(
                            name = "로그인 실패 예시",
                            value = """{ "status": 400, "state": "AUTH_LOGIN_FAILED", "message": "이메일 또는 비밀번호가 일치하지 않습니다." }"""
                        ),
                        ExampleObject(
                            name = "복합 필드 오류 예시",
                            value = """{ "status": 400, "state": "BAD_REQUEST", "message": "email: 이메일은 필수 입력 항목입니다., password: 비밀번호는 필수 입력 항목입니다." }"""
                        ),
                        ExampleObject(
                            name = "이메일 필드 오류 예시",
                            value = """{ "status": 400, "state": "BAD_REQUEST", "message": "email: 유효하지 않은 이메일 형식입니다." }"""
                        )
                    ]
                )]
            )
        ]
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

    @Operation(summary = "로그아웃", description = "사용중인 액세스 토큰을 무효화하고, 리프레시 토큰을 삭제합니다.")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200", description = "로그아웃 성공", content = [Content(
                    schema = Schema(implementation = BaseResponse::class), examples = [
                        ExampleObject(
                            name = "로그아웃 성공 예시",
                            value = """{ "status": 200, "message": "로그아웃 성공" }"""
                        )
                    ]
                )]
            ),
            ApiResponse(
                responseCode = "401", description = "인증 실패", content = [Content(
                    examples = [
                        ExampleObject(
                            name = "토큰 누락 예시",
                            value = """{ "status": 401, "state": "JWT_MISSING", "message": "토큰이 존재하지 않습니다." }"""
                        ),
                        ExampleObject(
                            name = "토큰 만료 예시",
                            value = """{ "status": 401, "state": "JWT_EXPIRED", "message": "토큰이 만료되었습니다." }"""
                        ),
                        ExampleObject(
                            name = "서명 불일치 예시",
                            value = """{ "status": 401, "state": "JWT_INVALID_SIGNATURE", "message": "서명이 일치하지 않습니다." }"""
                        ),
                        ExampleObject(
                            name = "유효하지 않은 토큰 예시",
                            value = """{ "status": 401, "state": "JWT_INVALID", "message": "유효하지 않은 토큰입니다." }"""
                        ),
                        ExampleObject(
                            name = "지원하지 않는 토큰 예시",
                            value = """{ "status": 401, "state": "JWT_UNSUPPORTED", "message": "지원하지 않는 토큰입니다." }"""
                        ),
                        ExampleObject(
                            name = "토큰 형식 오류 예시",
                            value = """{ "status": 401, "state": "JWT_MALFORMED", "message": "토큰 형식이 잘못되었습니다." }"""
                        ),
                        ExampleObject(
                            name = "알 수 없는 오류 예시",
                            value = """{ "status": 401, "state": "JWT_UNKNOWN_ERROR", "message": "알 수 없는 인증 오류입니다." }"""
                        )
                    ]
                )]
            )
        ]
    )
    fun logout(
        @Parameter(hidden = true) accessToken: String
    ): BaseResponse<Unit>

    @Operation(summary = "토큰 재발급", security = [], description = "리프레시 토큰으로 새로운 액세스 토큰을 발급받습니다.")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200", description = "토큰 재발급 성공", content = [Content(
                    schema = Schema(implementation = BaseResponse::class), examples = [
                        ExampleObject(
                            name = "토큰 재발급 성공 예시",
                            value = """{ "status": 200, "message": "토큰 재발급 성공", "data": "Bearer ey..." }"""
                        )
                    ]
                )]
            ),
            ApiResponse(
                responseCode = "400", description = "잘못된 요청", content = [Content(
                    examples = [
                        ExampleObject(
                            name = "리프레시 토큰 필드 오류 예시",
                            value = """{ "status": 400, "state": "BAD_REQUEST", "message": "refreshToken: 리프레시 토큰은 필수 입력 항목입니다." }"""
                        ),
                        ExampleObject(
                            name = "토큰 타입 불일치 예시",
                            value = """{ "status": 400, "state": "JWT_INVALID_TYPE", "message": "잘못된 토큰 타입입니다." }"""
                        )
                    ]
                )]
            ),
            ApiResponse(
                responseCode = "401", description = "인증 실패", content = [Content(
                    examples = [
                        ExampleObject(
                            name = "토큰 누락 예시",
                            value = """{ "status": 401, "state": "JWT_MISSING", "message": "토큰이 존재하지 않습니다." }"""
                        ),
                        ExampleObject(
                            name = "토큰 만료 예시",
                            value = """{ "status": 401, "state": "JWT_EXPIRED", "message": "토큰이 만료되었습니다." }"""
                        ),
                        ExampleObject(
                            name = "서명 불일치 예시",
                            value = """{ "status": 401, "state": "JWT_INVALID_SIGNATURE", "message": "서명이 일치하지 않습니다." }"""
                        ),
                        ExampleObject(
                            name = "유효하지 않은 토큰 예시",
                            value = """{ "status": 401, "state": "JWT_INVALID", "message": "유효하지 않은 토큰입니다." }"""
                        ),
                        ExampleObject(
                            name = "지원하지 않는 토큰 예시",
                            value = """{ "status": 401, "state": "JWT_UNSUPPORTED", "message": "지원하지 않는 토큰입니다." }"""
                        ),
                        ExampleObject(
                            name = "토큰 형식 오류 예시",
                            value = """{ "status": 401, "state": "JWT_MALFORMED", "message": "토큰 형식이 잘못되었습니다." }"""
                        ),
                        ExampleObject(
                            name = "알 수 없는 오류 예시",
                            value = """{ "status": 401, "state": "JWT_UNKNOWN_ERROR", "message": "알 수 없는 인증 오류입니다." }"""
                        )
                    ]
                )]
            ),
            ApiResponse(
                responseCode = "404", description = "존재하지 않는 정보", content = [Content(
                    examples = [
                        ExampleObject(
                            name = "존재하지 않는 회원 예시",
                            value = """{ "status": 404, "state": "MEMBER_NOT_FOUND", "message": "존재하지 않는 회원입니다." }"""
                        )
                    ]
                )]
            )
        ]
    )
    fun refresh(
        @RequestBody(
            required = true,
            description = "토큰 재발급 요청 DTO",
            content = [Content(
                mediaType = "application/json",
                schema = Schema(implementation = RefreshRequest::class),
                examples = [ExampleObject(
                    name = "토큰 재발급 요청 예시",
                    value = """
                    {
                      "refreshToken": "Bearer ey..."
                    }
                    """
                )]
            )]
        )
        request: RefreshRequest
    ): BaseResponse<String>
}
