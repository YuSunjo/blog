package com.blog.dto.upload

import jakarta.validation.constraints.NotNull


data class UploadRequest(
    @NotNull
    val imageType: ImageType
)