package com.blog.domain.member.repository

import com.blog.domain.member.Member
import com.blog.domain.member.Provider
import com.blog.domain.member.Role

interface MemberRepositoryCustom {

    fun findMemberByEmailAndRole(email: String, role: Role, provider: Provider): Member?

    fun findMemberById(memberId: Long) : Member?

    fun findMemberByIdAndRole(memberId: Long, role: Role): Member?

    fun findMemberByNickname(nickname: String): Member?

    fun findAllByIds(commentMemberIds: List<Long>): List<Member>

    fun findBySocialIdAndProvider(socialId: String, provider: Provider): Member?

}