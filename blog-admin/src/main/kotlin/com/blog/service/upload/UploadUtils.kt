package com.blog.service.upload

import com.blog.exception.ValidationException
import org.apache.commons.io.FilenameUtils
import java.text.SimpleDateFormat
import java.util.*

class UploadUtils {

    companion object {
        fun validateFileType(originalFilename: String?) {
            val typeList = Arrays.asList("jpg", "png", "jpeg")
            val type: String = FilenameUtils.getExtension(originalFilename)
            if (!typeList.contains(type)) {
                throw ValidationException("${type}는 허용되지 않는 파일형식입니다.")
            }
        }

        fun createFolder(uploadFolder: UploadFolder): String {
            return uploadFolder.toString() + "/"
        }

        fun createFileNameAndDirectory(originalFilename: String): String {
            val now = SimpleDateFormat("yyyyMMddHmsS").format(Date())
            val folder = createFolder(UploadFolder.USER)
            return folder + now + originalFilename
        }
    }


}