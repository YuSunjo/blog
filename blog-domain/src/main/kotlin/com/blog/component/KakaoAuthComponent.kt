package com.blog.component

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties(prefix = "kakao.auth")
data class KakaoAuthComponent(
    var clientKey: String = "",
    var grant_type: String = "",
    var url: String = "",
    var userInfoUrl: String = ""
)