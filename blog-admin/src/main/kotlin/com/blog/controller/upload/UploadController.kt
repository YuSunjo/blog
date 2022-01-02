package com.blog.controller.upload

import com.blog.ApiResponse
import com.blog.service.upload.UploadService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@RestController
class UploadController(
    private val uploadService: UploadService
) {
    @PostMapping("/image/upload")
    fun imageUpload(@RequestPart file: MultipartFile): ApiResponse<String> {
        return ApiResponse.success(uploadService.imageUpload(file))
    }
}