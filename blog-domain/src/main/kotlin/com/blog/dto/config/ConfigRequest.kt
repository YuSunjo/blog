package com.blog.dto.config

import com.blog.domain.config.Config
import jakarta.validation.constraints.NotBlank

data class CreateAndUpdateConfigRequest(
    @field:NotBlank
    var backgroundImage: String
) {
    fun toEntity(): Config {
        return Config(backgroundImage)
    }
}