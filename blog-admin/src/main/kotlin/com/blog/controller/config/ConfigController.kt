package com.blog.controller.config

import com.blog.ApiResponse
import com.blog.config.auth.Member
import com.blog.domain.config.Config
import com.blog.dto.config.CreateAndUpdateConfigRequest
import com.blog.service.config.ConfigService
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
class ConfigController(
    private val configService: ConfigService
) {

    @Member
    @PostMapping("api/v1/admin/config")
    fun createConfig(@RequestBody @Valid request: CreateAndUpdateConfigRequest): ApiResponse<Config> {
        return ApiResponse.success(configService.createConfig(request))
    }

    @Member
    @PutMapping("api/v1/admin/config")
    fun updateConfig(@RequestBody @Valid request: CreateAndUpdateConfigRequest): ApiResponse<Config> {
        return ApiResponse.success(configService.updateConfig(request))
    }

    @Member
    @GetMapping("api/v1/admin/config")
    fun getConfig(): ApiResponse<Config?> {
        return ApiResponse.success(configService.getConfig())
    }
}