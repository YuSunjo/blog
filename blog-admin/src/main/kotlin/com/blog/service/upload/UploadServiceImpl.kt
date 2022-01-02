package com.blog.service.upload

import com.amazonaws.services.s3.model.ObjectMetadata
import com.blog.exception.ValidationException
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.IOException
import java.io.InputStream

@Service
class UploadServiceImpl(
    private val s3Service: S3Service
) : UploadService {

    override fun imageUpload(file: MultipartFile): String {
        UploadUtils.validateFileType(file.originalFilename)
        val fileName: String = UploadUtils.createFileNameAndDirectory(file.originalFilename)
        val objectMetadata = ObjectMetadata()
        objectMetadata.contentLength = file.size
        objectMetadata.contentType = file.contentType
        try {
            val inputStream: InputStream = file.inputStream
            return s3Service.uploadFile(inputStream, objectMetadata, fileName)
        } catch (e: IOException) {
            throw ValidationException(String.format("%s 파일을 업로드하는데 오류가 발생했습니다.", file.originalFilename))
        }
    }

}