package com.blog.dto.auth

import com.blog.domain.member.Provider

data class AuthRequest(
    val email: String?,
    val name: String?,
    val provider: Provider,
    val accessToken: String
)

data class GoogleAuthRequest(
    val grantType: String,
    val code: String,
    val clientId: String,
    val clientSecret: String,
    val redirectUri: String,
)