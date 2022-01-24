package com.blog.controller.member

import com.blog.ApiResponse
import com.blog.config.argument.MemberId
import com.blog.config.auth.Member
import com.blog.dto.adminMember.CreateMemberRequest
import com.blog.dto.adminMember.LoginMemberRequest
import com.blog.dto.member.MemberInfoResponse
import com.blog.service.member.MemberService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
class MemberController(
    private val memberService: MemberService
) {

    @PostMapping("/api/v1/admin/member/signup")
    fun memberSignUp(@RequestBody @Valid request: CreateMemberRequest): ApiResponse<String> {
        memberService.createMember(request)
        return ApiResponse.OK
    }

    @PostMapping("/api/v1/admin/member/login")
    fun adminMemberLogin(@RequestBody @Valid request: LoginMemberRequest): ApiResponse<String> {
        return ApiResponse.success(memberService.adminMemberLogin(request))
    }

    @Member
    @GetMapping("/api/v1/admin/member")
    fun getMyInfo(@MemberId memberId: Long): ApiResponse<MemberInfoResponse?> {
        return ApiResponse.success(memberService.getMyInfo(memberId))
    }

}