package com.blog.dto.auth

import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming

data class GoogleMemberInfoResponse(
    val sub: String,
    val email: String,
    val picture: String,
    val name: String,
    val locale: String
)

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class GoogleAccessTokenResponse(
    val accessToken: String?,
    val refreshToken: String?,
    val expiresIn: String?,
    val idToken: String?,
)

data class FacebookMemberInfoResponse(
    val id: String,
    val name: String,
)