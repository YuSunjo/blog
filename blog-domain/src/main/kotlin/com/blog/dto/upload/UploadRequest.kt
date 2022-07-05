package com.blog.dto.upload

import com.sun.istack.NotNull

data class UploadRequest(
    @NotNull
    val imageType: ImageType
)