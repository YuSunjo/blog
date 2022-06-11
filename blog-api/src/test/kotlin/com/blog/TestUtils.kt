package com.blog

import com.blog.domain.comment.Comment
import com.blog.domain.member.Member
import com.blog.domain.member.Provider
import com.blog.dto.board.BoardInfoResponse
import org.assertj.core.api.Assertions.*

class TestUtils {

    companion object {

        fun assertBoardInfoResponse(boardInfoResponse: BoardInfoResponse, title: String, content: String, boardThumbnailUrl: String?) {
            assertThat(boardInfoResponse.title).isEqualTo(title)
            assertThat(boardInfoResponse.content).isEqualTo(content)
            assertThat(boardInfoResponse.boardThumbnailUrl).isEqualTo(boardThumbnailUrl)
        }

        fun assertComment(comment: Comment, content: String, parentCommentId: Long?, depth: Int) {
            assertThat(comment.content).isEqualTo(content)
            assertThat(comment.parentComment?.id).isEqualTo(parentCommentId)
            assertThat(comment.depth).isEqualTo(depth)
        }

        fun assertMember(member: Member, email: String?, nickname: String?, provider: Provider?) {
            assertThat(member.email).isEqualTo(email)
            assertThat(member.nickname).isEqualTo(nickname)
            assertThat(member.provider).isEqualTo(provider)
        }

    }

}