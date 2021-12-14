package com.blog.controller.auth

import com.blog.ApiResponse
import com.blog.dto.auth.AuthRequest
import com.blog.dto.auth.KaKaoUserInfoResponse
import com.blog.service.auth.AuthService
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
class AuthController(
    private val authService: AuthService
) {

    // test로 code 받기 위한 매핑
    @GetMapping("/auth/kakao/callback")
    @ResponseBody
    fun testOauth(code: String?): String? {
        return String.format("카카오 인증온료 코드: %s", code)
    }

    @PostMapping("/auth/kakao")
    fun kakaoAuthentication(@Valid @RequestBody request: AuthRequest): ApiResponse<KaKaoUserInfoResponse> {
        return ApiResponse.success(authService.kakaoAuthentication(request))
    }

}