package com.blog.service.upload

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@ConfigurationProperties(prefix = "cloud.aws.s3")
@Component
class S3Component {

    lateinit var bucket: String

}