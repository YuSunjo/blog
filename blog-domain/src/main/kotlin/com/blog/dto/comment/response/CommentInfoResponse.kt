package com.blog.dto.comment.response

import com.blog.domain.admin.Admin
import com.blog.domain.comment.Comment
import com.blog.dto.adminMember.AdminInfoResponse
import java.util.stream.Collectors

data class CommentInfoResponse(
    val commentId: Long,
    val content: String,
    val adminInfoResponse: AdminInfoResponse?,
    val childCommentList: MutableList<CommentInfoResponse> = ArrayList(),

    ) {
    companion object {
        fun of(comment: Comment, adminInfoResponse: AdminInfoResponse?): CommentInfoResponse {
            val boardChildCommentList = comment.childComments.stream()
                .map {
                    CommentInfoResponse.of(it, null)
                }.collect(Collectors.toList())
            val commentInfoResponse = CommentInfoResponse(comment.id, comment.content, adminInfoResponse)
            commentInfoResponse.childCommentList.addAll(boardChildCommentList)
            return commentInfoResponse
        }
    }
}
