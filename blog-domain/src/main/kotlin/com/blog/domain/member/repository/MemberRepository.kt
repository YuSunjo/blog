package com.blog.domain.member.repository

import com.blog.domain.member.Member
import org.springframework.data.jpa.repository.JpaRepository

interface MemberRepository: JpaRepository<Member, Long>, MemberRepositoryCustom {
}