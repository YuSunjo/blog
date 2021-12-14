package com.blog.service.auth

import com.blog.dto.auth.AuthRequest
import com.blog.dto.auth.KaKaoUserInfoResponse
import com.blog.dto.auth.KakaoAccessTokenResponse
import org.springframework.context.annotation.Configuration

@Configuration
interface AuthApiCaller {

    fun tokenAuthentication(request: AuthRequest): KakaoAccessTokenResponse?

    fun getUserInfo(accessToken: String): KaKaoUserInfoResponse?

}