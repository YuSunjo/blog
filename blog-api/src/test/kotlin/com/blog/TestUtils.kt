package com.blog

import com.blog.dto.board.BoardInfoResponse
import org.assertj.core.api.Assertions.*

class TestUtils {

    companion object {

        fun assertBoardInfoResponse(boardInfoResponse: BoardInfoResponse, title: String, content: String, boardThumbnailUrl: String?) {
            assertThat(boardInfoResponse.title).isEqualTo(title)
            assertThat(boardInfoResponse.content).isEqualTo(content)
            assertThat(boardInfoResponse.boardThumbnailUrl).isEqualTo(boardThumbnailUrl)
        }

    }

}