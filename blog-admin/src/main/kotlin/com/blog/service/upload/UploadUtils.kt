package com.blog.service.upload

import com.blog.dto.upload.ImageType
import com.blog.exception.ValidationException
import org.apache.commons.io.FilenameUtils
import java.text.SimpleDateFormat
import java.util.*

class UploadUtils {

    companion object {
        fun validateFileType(originalFilename: String?) {
            val typeList = listOf("jpg", "png", "jpeg")
            val type: String = FilenameUtils.getExtension(originalFilename)
            if (!typeList.contains(type)) {
                throw ValidationException("${type}는 허용되지 않는 파일형식입니다.")
            }
        }

        private fun createFolder(imageType: ImageType): String {
            return "$imageType/"
        }

        fun createFileNameAndDirectory(originalFilename: String?, imageType: ImageType): String {
            val now = SimpleDateFormat("yyyyMMddHmsS").format(Date())
            val folder = createFolder(imageType)
            return folder + now + originalFilename
        }

        fun replaceS3ToCloudFront(resourceUrl: String?, bucket: String, cloudFrontUrl: String): String? {
            val s3Url = String.format("https://%s.s3.ap-northeast-2.amazonaws.com", bucket)
            return resourceUrl?.replace(s3Url, cloudFrontUrl)
        }
    }
}