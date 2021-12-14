package com.blog.component

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties(prefix = "kakao.auth")
data class KakaoAuthComponent(
    val clientKey: String,
    val grant_type: String,
    val url: String,
    val userInfoUrl: String
)
