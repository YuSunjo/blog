package com.blog.domain.board.repository

import com.blog.domain.board.Board
import com.blog.domain.board.BoardHashTag
import com.blog.domain.board.QBoard.board
import com.blog.domain.board.QBoardHashTag.boardHashTag
import com.querydsl.core.types.dsl.BooleanExpression
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable

class BoardRepositoryCustomImpl(
    private val queryFactory: JPAQueryFactory
) : BoardRepositoryCustom {

    override fun findBoardById(id: Long): Board? {
        return queryFactory.selectFrom(board)
            .where(
                board.id.eq(id)
            )
            .fetchOne()
    }

    override fun findBySearchingPagination(
        pageable: Pageable,
        search: String?,
        category: String?,
        hashTagList: List<BoardHashTag>
    ): Page<Board> {
        val results = queryFactory.selectFrom(board).distinct()
            .leftJoin(board.boardHashTagList, boardHashTag)
            .where(
                eqTitle(search),
                eqCategory(category),
                eqHashTagList(hashTagList)
            )
            .offset(pageable.offset)
            .limit(pageable.pageSize.toLong())
            .orderBy(board.id.desc())
            .fetch()

        val total = queryFactory.selectFrom(board).distinct()
            .leftJoin(board.boardHashTagList, boardHashTag)
            .where(
                eqTitle(search),
                eqCategory(category),
                eqHashTagList(hashTagList)
            )
            .fetch()

        return PageImpl(results, pageable, total.size.toLong())
    }

    private fun eqTitle(search: String?): BooleanExpression? {
        if (search == null) {
            return null
        }
        return board.title.contains(search)
    }

    private fun eqCategory(category: String?): BooleanExpression? {
        if (category == null) {
            return null
        }
        return board.category.categoryName.eq(category)
    }

    private fun eqHashTagList(hashTagList: List<BoardHashTag>): BooleanExpression? {
        if (hashTagList.isEmpty()) {
            return null
        }
        return boardHashTag.`in`(hashTagList)
    }
}