package com.blog.service.upload

import com.amazonaws.services.s3.AmazonS3Client
import com.amazonaws.services.s3.model.CannedAccessControlList
import com.amazonaws.services.s3.model.ObjectMetadata
import com.amazonaws.services.s3.model.PutObjectRequest
import org.springframework.stereotype.Service
import java.io.InputStream

@Service
class S3Service(
    private val amazonS3Client: AmazonS3Client,
    private val s3Component: S3Component
) {
    fun uploadFile(inputStream: InputStream?, objectMetadata: ObjectMetadata?, fileName: String?): String? {
        amazonS3Client.putObject(
            PutObjectRequest(s3Component.bucket, fileName, inputStream, objectMetadata)
                .withCannedAcl(CannedAccessControlList.PublicRead)
        )
        val resourceUrl = amazonS3Client.getResourceUrl(s3Component.bucket, fileName)
        return UploadUtils.replaceS3ToCloudFront(resourceUrl, s3Component.bucket, s3Component.cloudFrontUrl)
    }
}