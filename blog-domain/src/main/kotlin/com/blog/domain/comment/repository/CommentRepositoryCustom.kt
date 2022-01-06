package com.blog.domain.comment.repository

import com.blog.domain.comment.Comment

interface CommentRepositoryCustom {

    fun findCommentById(commentId: Long): Comment?

    fun findCommentByBoardId(boardId: Long): List<Comment>

}