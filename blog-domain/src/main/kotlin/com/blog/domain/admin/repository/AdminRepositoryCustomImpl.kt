package com.blog.domain.admin.repository

import com.blog.domain.admin.Admin
import com.blog.domain.admin.QAdmin.admin
import com.querydsl.jpa.impl.JPAQueryFactory

class AdminRepositoryCustomImpl(
    private val queryFactory: JPAQueryFactory
) : AdminRepositoryCustom {

    override fun findByEmail(email: String): Admin? {
        return queryFactory.selectFrom(admin)
            .where(
                admin.email.eq(email)
            )
            .fetchOne()
    }

    override fun findAdminById(memberId: Long): Admin? {
        return queryFactory.selectFrom(admin)
                .where(
                        admin.id.eq(memberId)
                )
                .fetchOne()
    }

}