package com.blog.dto.member

import com.blog.domain.member.Member
import com.blog.domain.member.Provider

data class MemberInfoResponse(
    var id: Long,
    var email: String,
    var memberImage: String?,
    var provider: Provider
) {
    companion object {
        fun of(member: Member): MemberInfoResponse {
            return MemberInfoResponse(member.id, member.email, member.memberImage, member.provider)
        }
    }
}