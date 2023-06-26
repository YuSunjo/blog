package com.blog.config.aws

import com.amazonaws.auth.AWSStaticCredentialsProvider
import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.AmazonS3ClientBuilder
import com.blog.component.AmazonS3Component
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


@Configuration
class AmazonConfig(
    private val amazonS3Component: AmazonS3Component,
) {

    @Bean
    fun amazonS3Client(): AmazonS3? {
        val credentials = BasicAWSCredentials(amazonS3Component.accessKey, amazonS3Component.secretKey)
        return AmazonS3ClientBuilder
            .standard()
            .withRegion("ap-northeast-2")
            .withCredentials(AWSStaticCredentialsProvider(credentials))
            .build()
    }
}