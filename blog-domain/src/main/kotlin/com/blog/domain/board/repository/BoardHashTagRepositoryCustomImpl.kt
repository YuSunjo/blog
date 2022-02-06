package com.blog.domain.board.repository

import com.blog.domain.board.Board
import com.blog.domain.board.BoardHashTag
import com.blog.domain.board.QBoardHashTag.*
import com.querydsl.jpa.impl.JPAQueryFactory
import lombok.RequiredArgsConstructor

@RequiredArgsConstructor
class BoardHashTagRepositoryCustomImpl(
    private val queryFactory: JPAQueryFactory
): BoardHashTagRepositoryCustom
 {

     override fun findHashTagByBoard(board: Board): List<BoardHashTag> {
         return queryFactory.selectFrom(boardHashTag)
             .where(
                 boardHashTag.board.eq(board)
             )
             .fetch()
     }

     override fun findDistinctHashTag(): List<String> {
         return queryFactory.select(boardHashTag.hashTag).distinct()
             .from(boardHashTag)
             .fetch()
     }

 }