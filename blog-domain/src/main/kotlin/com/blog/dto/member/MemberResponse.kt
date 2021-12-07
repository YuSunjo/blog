package com.blog.dto.member

import com.blog.domain.member.Member

data class MemberInfoResponse(
    var id: Long,
    var email: String,
    var memberImage: String?,
) {
    companion object {
        fun of(member: Member): MemberInfoResponse {
            return MemberInfoResponse(member.id, member.email, member.memberImage)
        }
    }
}