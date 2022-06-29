package com.blog.adminService

import com.blog.domain.board.Board
import com.blog.domain.comment.Comment
import com.blog.dto.board.BoardInfoResponse
import org.assertj.core.api.Assertions.*

class TestUtils {

    companion object {
        fun assertBoard(board: Board, title: String, content: String, boardThumbnailUrl: String?) {
            assertThat(board.title).isEqualTo(title)
            assertThat(board.content).isEqualTo(content)
            assertThat(board.boardThumbnailUrl).isEqualTo(boardThumbnailUrl)
        }

        fun assertBoardInfoResponse(
            boardInfoResponse: BoardInfoResponse,
            title: String,
            content: String,
            boardThumbnailUrl: String?
        ) {
            assertThat(boardInfoResponse.title).isEqualTo(title)
            assertThat(boardInfoResponse.content).isEqualTo(content)
            assertThat(boardInfoResponse.boardThumbnailUrl).isEqualTo(boardThumbnailUrl)
        }

        fun assertComment(comment: Comment, content: String, parentCommentId: Long?, depth: Int) {
            assertThat(comment.content).isEqualTo(content)
            assertThat(comment.parentComment?.id).isEqualTo(parentCommentId)
            assertThat(comment.depth).isEqualTo(depth)
        }
    }
}