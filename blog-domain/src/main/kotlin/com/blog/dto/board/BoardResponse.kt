package com.blog.dto.board

import com.blog.domain.board.Board
import com.blog.domain.board.BoardHashTag
import java.time.LocalDateTime

data class BoardInfoResponse(
    var id: Long = 0L,
    var title: String = "",
    var content: String = "",
    var isPrivate: Boolean = false,
    var boardThumbnailUrl: String?,
    var categoryName: String,
    var categoryId: Long = 0L,
    var memberId: Long = 0L,
    var createdDate: LocalDateTime?,
    var hashTagList: MutableList<BoardHashTagInfoResponse>
) {
    companion object {
        fun of(board: Board): BoardInfoResponse {
            return BoardInfoResponse(
                board.id, board.title, board.content, board.isPrivate, board.boardThumbnailUrl,
                board.category.categoryName, board.category.id, board.memberId, board.createdDate, board.getBoardHashTagInfoResponseList()
            )
        }
    }
}

data class BoardInfoListResponse(
    var boardList: List<BoardInfoResponse>,
    var totalPage: Int
) {
    companion object {
        fun of(boardList: List<BoardInfoResponse>, totalPage: Int): BoardInfoListResponse {
            return BoardInfoListResponse(boardList, totalPage)
        }
    }
}

data class BoardHashTagInfoResponse(
    var hashTag: String,
) {
    companion object {
        fun of(boardHashTag: BoardHashTag): BoardHashTagInfoResponse {
            return BoardHashTagInfoResponse(boardHashTag.hashTag)
        }
    }
}