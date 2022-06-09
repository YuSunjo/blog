package com.blog.service.auth.factory

import com.blog.domain.member.Member
import com.blog.dto.auth.AuthRequest

interface AuthFactoryService {

    fun findSocialIdAndProvider(request: AuthRequest): Member

}