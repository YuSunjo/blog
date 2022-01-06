package com.blog.domain.comment.repository

import com.blog.domain.comment.Comment
import com.blog.domain.comment.QComment
import com.blog.domain.comment.QComment.comment
import com.querydsl.jpa.impl.JPAQueryFactory
import java.util.*

class CommentRepositoryCustomImpl(
    private val queryFactory: JPAQueryFactory
) : CommentRepositoryCustom {

    override fun findCommentById(commentId: Long): Comment? {
        return queryFactory.selectFrom(comment)
            .where(
                comment.id.eq(commentId)
            )
            .fetchOne()
    }

    override fun findCommentByBoardId(boardId: Long): List<Comment> {
        return queryFactory.selectFrom(comment)
            .leftJoin(comment.childComments, QComment("child")).fetchJoin()
            .where(
                comment.boardId.eq(boardId)
            )
            .fetch()
    }

}