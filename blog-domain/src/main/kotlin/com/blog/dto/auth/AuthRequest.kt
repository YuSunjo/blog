package com.blog.dto.auth

data class AuthRequest(
    val code: String,
    val redirectUri: String
)

data class GoogleAuthRequest(
    val grantType: String,
    val code: String,
    val clientId: String,
    val clientSecret: String,
    val redirectUri: String,
)