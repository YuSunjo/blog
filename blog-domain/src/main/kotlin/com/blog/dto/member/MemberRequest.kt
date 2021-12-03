package com.blog.dto.member

import com.blog.domain.admin.Admin
import javax.validation.constraints.NotBlank

data class CreateMemberRequest(
    @field:NotBlank
    var email: String = "",

    @field:NotBlank
    var password: String = ""
) {
    fun toEntity(encodedPassword: String): Admin {
        return Admin(email, encodedPassword, null)
    }

}

data class LoginMemberRequest(
    @field:NotBlank
    var email: String = "",

    @field:NotBlank
    var password: String = ""
)