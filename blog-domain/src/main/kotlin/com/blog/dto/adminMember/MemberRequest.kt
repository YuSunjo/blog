package com.blog.dto.adminMember

import com.blog.domain.member.Member
import com.blog.domain.member.Provider
import com.blog.domain.member.Role
import javax.validation.constraints.NotBlank

data class CreateMemberRequest(
    @field:NotBlank
    var email: String,

    @field:NotBlank
    var password: String
) {
    fun toEntity(encodedPassword: String): Member {
        return Member(email, encodedPassword, null, Provider.LOCAL, Role.ADMIN)
    }

}

data class LoginMemberRequest(
    @field:NotBlank
    var email: String = "",

    @field:NotBlank
    var password: String = ""
)