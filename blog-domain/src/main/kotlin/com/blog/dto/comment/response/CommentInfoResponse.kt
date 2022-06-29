package com.blog.dto.comment.response

import com.blog.domain.comment.Comment
import com.blog.domain.member.Member
import com.blog.dto.member.MemberInfoResponse
import java.util.stream.Collectors

data class CommentInfoResponse(
    val commentId: Long,
    val content: String,
    val memberInfoResponse: MemberInfoResponse?,
    val childCommentList: MutableList<CommentInfoResponse> = ArrayList(),

) {
    companion object {
        fun of(comment: Comment, memberMap: Map<Long, Member>): CommentInfoResponse {
            val boardChildCommentList = comment.childComments.stream()
                .map {
                    of(it, memberMap)
                }.collect(Collectors.toList())
            val member: Member? = memberMap[comment.memberId]
            val commentInfoResponse = CommentInfoResponse(comment.id, comment.content, MemberInfoResponse.of(member))
            commentInfoResponse.childCommentList.addAll(boardChildCommentList)
            return commentInfoResponse
        }
    }
}