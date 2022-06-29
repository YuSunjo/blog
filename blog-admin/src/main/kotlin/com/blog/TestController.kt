package com.blog

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class TestController {

    @GetMapping("/ping")
    fun ping(): ApiResponse<String> {
        return ApiResponse.OK
    }
}