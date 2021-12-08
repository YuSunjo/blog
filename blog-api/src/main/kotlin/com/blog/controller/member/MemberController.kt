package com.blog.controller.member

import com.blog.ApiResponse
import com.blog.config.argument.MemberId
import com.blog.config.auth.Member
import com.blog.dto.member.CreateMemberRequest
import com.blog.dto.member.LoginMemberRequest
import com.blog.dto.member.MemberInfoResponse
import com.blog.service.MemberService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
class MemberController(
    private val memberService: MemberService
) {
    @PostMapping("/api/v1/member")
    fun memberSignUp(@RequestBody @Valid request: CreateMemberRequest): ApiResponse<MemberInfoResponse> {
        return ApiResponse.success(memberService.memberSignUp(request))
    }

    @PostMapping("/api/v1/member/login")
    fun memberLogin(@RequestBody @Valid request: LoginMemberRequest): ApiResponse<String> {
        return ApiResponse.success(memberService.memberLogin(request))
    }

    @Member
    @GetMapping("/api/v1/member")
    fun getMyInfo(@MemberId memberId: Long): ApiResponse<MemberInfoResponse> {
        return ApiResponse.success(memberService.getMember(memberId))
    }
}