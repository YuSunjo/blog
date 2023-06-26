package com.blog.component

import lombok.Getter
import lombok.Setter
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "cloud.aws.credentials")
class AmazonS3Component {

    var accessKey: String = ""
    var secretKey: String = ""

}