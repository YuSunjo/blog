package com.blog.domain.category.repository

import com.blog.domain.category.Category
import com.blog.domain.category.QCategory.category
import com.querydsl.jpa.impl.JPAQueryFactory

class CategoryRepositoryCustomImpl(
        private val queryFactory: JPAQueryFactory
): CategoryRepositoryCustom {
    override fun findCategoryById(id: Long): Category? {
        return queryFactory.selectFrom(category)
                .where(
                        category.id.eq(id)
                )
                .fetchOne()
    }

    override fun findCategory(): List<Category> {
        return queryFactory.selectFrom(category)
                .fetch()
    }
}