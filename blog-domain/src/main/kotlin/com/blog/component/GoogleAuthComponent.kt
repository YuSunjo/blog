package com.blog.component

import lombok.Getter
import lombok.Setter
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "google.auth")
class GoogleAuthComponent {

    var clientId: String = ""
    var clientSecret: String = ""
    var grantType: String = ""
    var baseUrl: String = ""
    var tokenUrl: String = ""
    var profileUrl: String = ""

}