package com.blog.domain.member.repository

import com.blog.domain.member.Member
import com.blog.domain.member.QMember.member
import com.querydsl.jpa.impl.JPAQueryFactory

class MemberRepositoryCustomImpl(
    private val queryFactory: JPAQueryFactory
): MemberRepositoryCustom{

    override fun findMemberByEmail(email: String): Member? {
        return queryFactory.selectFrom(member)
            .where(
                member.email.eq(email)
            )
            .fetchOne()
    }

    override fun findMemberById(memberId: Long): Member? {
        return queryFactory.selectFrom(member)
            .where(
                member.id.eq(memberId)
            )
            .fetchOne()
    }

}