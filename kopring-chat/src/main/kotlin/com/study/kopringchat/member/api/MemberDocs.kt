package com.study.kopringchat.member.api

import com.study.kopringchat.global.common.BaseResponse
import com.study.kopringchat.member.api.dto.request.RegisterMemberRequest
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.ExampleObject
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.parameters.RequestBody
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag

@Tag(name = "Member", description = "회원 관련 API")
interface MemberDocs {

    @Operation(
        summary = "회원가입",
        description = "새로운 회원을 등록합니다."
    )
    @ApiResponse(
        responseCode = "201",
        description = "회원가입 성공",
        content = [Content(
            mediaType = "application/json",
            schema = Schema(implementation = BaseResponse::class),
            examples = [ExampleObject(
                name = "회원가입 성공 응답 예시",
                value = """
                {
                    "status": 201,
                    "message": "회원가입 성공",
                    "data": 1
                }
                """
            )]
        )]
    )
    fun register(
        @RequestBody(
            required = true,
            description = "회원가입 요청 DTO",
            content = [Content(
                mediaType = "application/json",
                schema = Schema(implementation = RegisterMemberRequest::class),
                examples = [ExampleObject(
                    name = "회원가입 요청 예시",
                    value = """
                    {
                      "email": "test@example.com",
                      "name": "홍길동",
                      "password": "password123!"
                    }
                    """
                )]
            )]
        )
        request: RegisterMemberRequest
    ): BaseResponse<Long>
}
