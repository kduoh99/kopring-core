package com.study.kopringchat.member.api

import com.study.kopringchat.global.common.BaseResponse
import com.study.kopringchat.member.api.dto.request.RegisterMemberRequest
import com.study.kopringchat.member.application.MemberService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/member")
class MemberController(
    private val memberService: MemberService
) : MemberDocs {

    @PostMapping("/register")
    override fun register(@Valid @RequestBody request: RegisterMemberRequest): BaseResponse<Long> {
        return BaseResponse(
            status = HttpStatus.CREATED,
            message = "회원가입 성공",
            data = memberService.registerMember(request)
        )
    }
}
