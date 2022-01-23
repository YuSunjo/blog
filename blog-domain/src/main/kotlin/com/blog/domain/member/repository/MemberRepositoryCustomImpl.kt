package com.blog.domain.member.repository

import com.blog.domain.member.Member
import com.blog.domain.member.QMember.member
import com.blog.domain.member.Role
import com.querydsl.jpa.impl.JPAQueryFactory

class MemberRepositoryCustomImpl(
    private val queryFactory: JPAQueryFactory
): MemberRepositoryCustom{

    override fun findMemberByEmailAndRole(email: String, role: Role): Member? {
        return queryFactory.selectFrom(member)
            .where(
                member.email.eq(email),
                member.role.eq(role)
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

    // 어드민한테만 쓰이는 쿼리
    override fun findMemberByIdIsAdmin(memberId: Long): Member? {
        return queryFactory.selectFrom(member)
            .where(
                member.id.eq(memberId),
                member.role.eq(Role.ADMIN)
            )
            .fetchOne()
    }

    override fun findMemberByNickname(nickname: String): Member? {
        return queryFactory.selectFrom(member)
            .where(
                member.nickname.eq(nickname)
            )
            .fetchOne()
    }

    override fun findAllByIds(commentMemberIds: List<Long>): List<Member> {
        return queryFactory.selectFrom(member)
            .where(
                member.id.`in`(commentMemberIds)
            )
            .fetch()
    }

}