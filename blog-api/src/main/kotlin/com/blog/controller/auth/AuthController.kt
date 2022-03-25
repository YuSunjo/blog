package com.blog.controller.auth

import com.blog.ApiResponse
import com.blog.dto.auth.AuthRequest
import com.blog.service.auth.AuthService
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
class AuthController(
    private val authService: AuthService
) {

    // test 로 code 받기 위한 매핑
    @GetMapping("login/oauth2/code/google")
    fun testOauth(code: String?): String? {
        return code
    }

    @PostMapping("auth/google")
    fun googleAuthentication(@Valid @RequestBody request: AuthRequest): ApiResponse<String> {
        return ApiResponse.success(authService.googleAuthentication(request))
    }

}