package com.blog.dto.member

import com.blog.domain.member.Member
import com.blog.domain.member.Provider
import com.blog.domain.member.Role
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank

data class CreateMemberRequest(
    @field:Email
    var email: String,
    @field:NotBlank
    var password: String,
    var memberImage: String?,
) {
    var provider: Provider = Provider.LOCAL

    constructor(email: String, password: String, memberImage: String?, provider: Provider): this(email, password, memberImage) {
        this.provider = provider
    }

    fun toEntity(encodedPassword: String): Member {
        return Member(email, encodedPassword, memberImage, provider, Role.ADMIN)
    }
}

data class LoginMemberRequest(
    @field:Email
    var email: String,
    @field:NotBlank
    var password: String,
) {
    var provider: Provider = Provider.LOCAL

    constructor(email: String, password: String, provider: Provider): this(email, password) {
        this.provider = provider
    }
}