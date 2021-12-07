package com.blog.dto.adminMember

import com.blog.domain.admin.Admin

data class AdminInfoResponse(
        var id: Long,
        var email: String,
        var adminImage: String?
) {
    companion object {
        fun of(admin: Admin): AdminInfoResponse {
            return AdminInfoResponse(admin.id, admin.email, admin.adminImage)
        }
    }
}