package com.blog.service.upload

import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.model.CannedAccessControlList
import com.amazonaws.services.s3.model.ObjectMetadata
import com.amazonaws.services.s3.model.PutObjectRequest
import org.springframework.stereotype.Service
import java.io.InputStream

@Service
class S3Service(
    private val amazonS3: AmazonS3,
    private val s3Component: S3Component
) {
    fun uploadFile(inputStream: InputStream?, objectMetadata: ObjectMetadata?, fileName: String?): String? {
        amazonS3.putObject(
            PutObjectRequest(s3Component.bucket, fileName, inputStream, objectMetadata)
                .withCannedAcl(CannedAccessControlList.PublicRead)
        )
        val resourceUrl = amazonS3.getUrl(s3Component.bucket, fileName)
        return UploadUtils.replaceS3ToCloudFront(resourceUrl.path, s3Component.bucket, s3Component.cloudFrontUrl)
    }
}