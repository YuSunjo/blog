package com.blog.dto.member

import com.blog.domain.member.Member
import com.blog.domain.member.Provider
import com.blog.domain.member.Role

data class MemberInfoResponse(
    var id: Long,
    var email: String,
    var memberImage: String?,
    var provider: Provider,
    var role: Role,
    var nickname: String
) {
    companion object {
        fun of(member: Member): MemberInfoResponse {
            return MemberInfoResponse(member.id, member.email, member.memberImage, member.provider, member.role, member.nickname)
        }
    }
}