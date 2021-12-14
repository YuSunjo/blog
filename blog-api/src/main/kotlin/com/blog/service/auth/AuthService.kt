package com.blog.service.auth

import com.blog.dto.auth.AuthRequest
import com.blog.dto.auth.KaKaoUserInfoResponse
import com.blog.exception.NotFoundException
import org.springframework.stereotype.Service

@Service
class AuthService(
    private val kakaoApiCaller: AuthApiCaller
) {
    fun kakaoAuthentication(request: AuthRequest): KaKaoUserInfoResponse {
        val kakaoAccessTokenResponse = (kakaoApiCaller.tokenAuthentication(request)
            ?: throw NotFoundException("accessToken을 가지고 오지 못했습니다."))
        return kakaoApiCaller.getUserInfo(kakaoAccessTokenResponse.accessToken)
            ?: throw NotFoundException("user 정보를 가지고 오지 못헀습니다")
    }

}