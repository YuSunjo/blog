package com.blog.dto.auth

data class KakaoAccessTokenResponse(
    val accessToken: String,
    val tokenType: String,
    val refreshToken: String,
    val expiresIn: Int,
    val scope: String,
    val refreshTokenExpiresIn: Int
)


data class KaKaoUserInfoResponse(
    val kakaoAccount: KakaoUserInfoAccount
)


data class KakaoUserInfoAccount(
    val kakaoProfile: KakaoProfile,
    val email: String
)

data class KakaoProfile(
    val nickname: String,
    val profileImageUrl: String
)