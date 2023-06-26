package com.blog.controller.auth

import com.blog.ApiResponse
import com.blog.dto.auth.AuthRequest
import com.blog.service.auth.factory.AuthFactory
import com.blog.service.member.MemberService
import io.swagger.annotations.ApiOperation
import org.springframework.web.bind.annotation.*
import jakarta.validation.Valid

@RestController
class AuthController(
    private val authFactory: AuthFactory,
    private val memberService: MemberService
) {

    // test 로 code 받기 위한 매핑
    @GetMapping("login/oauth2/code/google")
    fun testOauth(code: String?): String? {
        return code
    }

    @ApiOperation("소셜 로그인 api - 구글 - socialId(고유 아이디), 페이스북: access_token ")
    @PostMapping("api/v1/auth")
    fun googleAuthentication(@Valid @RequestBody request: AuthRequest): ApiResponse<String> {
        val authService = authFactory.getAuthService(request.provider)
        val member = authService.findSocialIdAndProvider(request)
        return ApiResponse.success(memberService.authMemberLogin(member))
    }
}