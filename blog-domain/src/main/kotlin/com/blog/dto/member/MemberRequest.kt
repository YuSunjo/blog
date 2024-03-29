package com.blog.dto.member

import com.blog.domain.member.Member
import com.blog.domain.member.Provider
import com.blog.domain.member.Role
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank

data class CreateMemberRequest(
    @field:Email
    var email: String,
    @field:NotBlank
    var password: String,
    var memberImage: String?,

    @field:NotBlank
    var nickname: String
) {
    var provider: Provider = Provider.LOCAL

    constructor(email: String, password: String, memberImage: String?, provider: Provider, nickname: String) : this(email, password, memberImage, nickname) {
        this.provider = provider
    }

    fun toEntity(encodedPassword: String): Member {
        return Member(email, encodedPassword, memberImage, provider, Role.USER, nickname, null)
    }
}

data class LoginMemberRequest(
    @field:Email
    var email: String,
    @field:NotBlank
    var password: String,
) {
    var provider: Provider = Provider.LOCAL

    constructor(email: String, password: String, provider: Provider) : this(email, password) {
        this.provider = provider
    }
}