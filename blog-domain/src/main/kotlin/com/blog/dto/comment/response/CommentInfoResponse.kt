package com.blog.dto.comment.response

import com.blog.domain.comment.Comment
import com.blog.dto.member.MemberInfoResponse
import java.util.stream.Collectors

data class CommentInfoResponse(
    val commentId: Long,
    val content: String,
    val memberInfoResponse: MemberInfoResponse?,
    val childCommentList: MutableList<CommentInfoResponse> = ArrayList(),

    ) {
    companion object {
        fun of(comment: Comment, memberInfoResponse: MemberInfoResponse?): CommentInfoResponse {
            val boardChildCommentList = comment.childComments.stream()
                .map {
                    of(it, null)
                }.collect(Collectors.toList())
            val commentInfoResponse = CommentInfoResponse(comment.id, comment.content, memberInfoResponse)
            commentInfoResponse.childCommentList.addAll(boardChildCommentList)
            return commentInfoResponse
        }
    }
}
