package com.blog.service

import com.blog.domain.board.Board
import com.blog.dto.board.BoardInfoResponse
import org.assertj.core.api.Assertions

class TestUtils {

    companion object {
        fun assertBoard(board: Board, title: String, content: String, boardThumbnailUrl: String?) {
            Assertions.assertThat(board.title).isEqualTo(title)
            Assertions.assertThat(board.content).isEqualTo(content)
            Assertions.assertThat(board.boardThumbnailUrl).isEqualTo(boardThumbnailUrl)
        }

        fun assertBoardInfoResponse(boardInfoResponse: BoardInfoResponse, title: String, content: String, boardThumbnailUrl: String?) {
            Assertions.assertThat(boardInfoResponse.title).isEqualTo(title)
            Assertions.assertThat(boardInfoResponse.content).isEqualTo(content)
            Assertions.assertThat(boardInfoResponse.boardThumbnailUrl).isEqualTo(boardThumbnailUrl)
        }
    }

}