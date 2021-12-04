package com.blog.domain.admin.repository

import com.blog.domain.admin.Admin

interface AdminRepositoryCustom {

    fun findByEmail(email: String): Admin?

    fun findAdminById(memberId: Long): Admin?

}