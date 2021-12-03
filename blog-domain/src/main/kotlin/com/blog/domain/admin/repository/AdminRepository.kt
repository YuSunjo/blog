package com.blog.domain.admin.repository

import com.blog.domain.admin.Admin
import org.springframework.data.jpa.repository.JpaRepository

interface AdminRepository: JpaRepository<Admin, Long>, AdminRepositoryCustom {
}