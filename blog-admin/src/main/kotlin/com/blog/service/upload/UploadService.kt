package com.blog.service.upload

import com.blog.dto.upload.ImageType
import org.springframework.web.multipart.MultipartFile

interface UploadService {

    fun imageUpload(file: MultipartFile, imageType: ImageType): String?
}