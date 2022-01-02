package com.blog.controller.upload;

import com.blog.ApiResponse;
import com.blog.service.upload.UploadService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
public class UploadController {

    private final UploadService uploadService;

    @ApiOperation(value = "업로드", notes = "이미지 파일 업로드")
    @PostMapping("api/v1/admin/image/upload")
    public ApiResponse<String> imageUpload(@RequestPart MultipartFile file) {
        return ApiResponse.Companion.success(uploadService.imageUpload(file));
    }

}