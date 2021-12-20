package com.blog.dto.auth

data class AuthRequest(
    val code: String,
    val redirectUri: String
)