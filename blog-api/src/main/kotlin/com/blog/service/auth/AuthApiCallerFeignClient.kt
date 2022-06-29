package com.blog.service.auth

import com.blog.config.FeignClientConfig
import com.blog.dto.auth.GoogleAccessTokenResponse
import com.blog.dto.auth.GoogleAuthRequest
import com.blog.dto.auth.GoogleMemberInfoResponse
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.http.HttpHeaders
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader

@FeignClient(
    name = "googleApiCallerFeignClient",
    url = "\${google.auth.base_url}",
    configuration = [FeignClientConfig::class]
)
interface AuthApiCallerFeignClient {

    @PostMapping("\${google.auth.token_url}")
    fun tokenAuthentication(@RequestBody request: GoogleAuthRequest?): GoogleAccessTokenResponse

    @GetMapping("\${google.auth.profile_url}")
    fun getGoogleMemberInfo(@RequestHeader(HttpHeaders.AUTHORIZATION) accessToken: String?): GoogleMemberInfoResponse
}