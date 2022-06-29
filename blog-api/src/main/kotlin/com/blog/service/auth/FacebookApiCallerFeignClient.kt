package com.blog.service.auth

import com.blog.config.FeignClientConfig
import com.blog.dto.auth.FacebookMemberInfoResponse
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam

@FeignClient(
    name = "facebookApiCallerFeignClient",
    url = "\${facebook.auth.base_url}",
    configuration = [FeignClientConfig::class]
)
interface FacebookApiCallerFeignClient {

    @GetMapping("\${facebook.auth.profile_url}")
    fun getFacebookMemberInfo(@RequestParam access_token: String, @RequestParam fields: List<String>): FacebookMemberInfoResponse
}