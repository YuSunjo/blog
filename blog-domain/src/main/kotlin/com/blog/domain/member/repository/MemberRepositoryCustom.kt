package com.blog.domain.member.repository

import com.blog.domain.member.Member

interface MemberRepositoryCustom {

    fun findMemberByEmail(email: String): Member?

}