package com.blog.service.upload

import org.springframework.web.multipart.MultipartFile

interface UploadService {

    fun imageUpload (file: MultipartFile): String?

}