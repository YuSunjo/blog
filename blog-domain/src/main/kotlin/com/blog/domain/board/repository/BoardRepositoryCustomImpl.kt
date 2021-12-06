package com.blog.domain.board.repository

import com.blog.domain.board.Board
import com.blog.domain.board.QBoard.board
import com.querydsl.core.types.Order
import com.querydsl.core.types.OrderSpecifier
import com.querydsl.core.types.dsl.BooleanExpression
import com.querydsl.core.types.dsl.PathBuilder
import com.querydsl.jpa.impl.JPAQuery
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort

class BoardRepositoryCustomImpl(
    private val queryFactory: JPAQueryFactory
): BoardRepositoryCustom {

    override fun findBoardById(id: Long): Board? {
        return queryFactory.selectFrom(board)
            .where(
                board.id.eq(id)
            )
            .fetchOne()
    }

    override fun findBySearchingPagination(pageable: Pageable, search: String?): Page<Board> {
        val results = queryFactory.selectFrom(board)
            .where(
                eqTitle(search),
            )
            .offset(pageable.offset)
            .limit(pageable.pageSize.toLong())
            .orderBy(board.id.desc())
            .fetchResults()

        val content = results.results
        val total = results.total
        return PageImpl(content, pageable, total)
    }

    private fun eqTitle(search: String?): BooleanExpression? {
        if (search == null) {
            return null
        }
        return board.title.contains(search)
    }

}