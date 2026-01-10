package com.study.kopringchat.member.api

import com.study.kopringchat.global.common.BaseResponse
import com.study.kopringchat.member.api.dto.request.RegisterMemberRequest
import com.study.kopringchat.member.api.dto.response.FindMembersResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.ExampleObject
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.parameters.RequestBody
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag

@Tag(name = "Member", description = "회원 관련 API")
interface MemberDocs {

    @Operation(summary = "회원가입", security = [], description = "신규 회원을 등록합니다.")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "201", description = "회원가입 성공", content = [Content(
                    schema = Schema(implementation = BaseResponse::class), examples = [
                        ExampleObject(
                            name = "회원가입 성공 예시",
                            value = """{ "status": 201, "message": "회원가입 성공", "data": 1 }"""
                        )
                    ]
                )]
            ),
            ApiResponse(
                responseCode = "400", description = "잘못된 요청", content = [Content(
                    examples = [
                        ExampleObject(
                            name = "복합 필드 오류 예시",
                            value = """{ "status": 400, "state": "BAD_REQUEST", "message": "name: 이름은 필수 입력 항목입니다., name: 이름은 2자 이상 20자 이하로 입력해 주세요., password: 비밀번호는 최소 8자 이상이어야 합니다., email: 이메일은 필수 입력 항목입니다." }"""
                        ),
                        ExampleObject(
                            name = "이메일 필드 오류 예시",
                            value = """{ "status": 400, "state": "BAD_REQUEST", "message": "email: 유효하지 않은 이메일 형식입니다." }"""
                        ),
                        ExampleObject(
                            name = "이름 필드 오류 예시",
                            value = """{ "status": 400, "state": "BAD_REQUEST", "message": "name: 이름은 2자 이상 20자 이하로 입력해 주세요." }"""
                        ),
                        ExampleObject(
                            name = "비밀번호 필드 오류 예시",
                            value = """{ "status": 400, "state": "BAD_REQUEST", "message": "password: 비밀번호는 최소 8자 이상이어야 합니다." }"""
                        )
                    ]
                )]
            ),
            ApiResponse(
                responseCode = "409", description = "중복된 데이터", content = [Content(
                    examples = [
                        ExampleObject(
                            name = "이메일 중복 예시",
                            value = """{ "status": 409, "state": "MEMBER_ALREADY_EXIST", "message": "이미 존재하는 회원입니다." }"""
                        )
                    ]
                )]
            )
        ]
    )
    fun register(
        @RequestBody(
            description = "회원가입 요청 DTO",
            content = [Content(
                mediaType = "application/json",
                schema = Schema(implementation = RegisterMemberRequest::class),
                examples = [ExampleObject(
                    name = "회원가입 요청 예시",
                    value = """{ "email": "test@example.com", "name": "홍길동", "password": "password123!" }"""
                )]
            )]
        )
        request: RegisterMemberRequest
    ): BaseResponse<Long>

    @Operation(summary = "회원 목록 조회", description = "가입된 모든 회원 목록을 조회합니다.")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200", description = "회원 목록 조회 성공", content = [Content(
                    mediaType = "application/json", schema = Schema(implementation = BaseResponse::class), examples = [
                        ExampleObject(
                            name = "회원 목록 조회 성공 예시",
                            value = """{ "status": 200, "message": "회원 목록 조회 성공", "data": [ { "memberId": 1, "name": "홍길동", "email": "test@example.com" } ] }"""
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
    fun findMembers(): BaseResponse<List<FindMembersResponse>>
}
