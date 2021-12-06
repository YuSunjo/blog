package com.blog.dto.board

import com.blog.domain.board.Board

data class BoardInfoResponse(
    var id: Long = 0L,
    var title: String = "",
    var content: String = "",
    var isPrivate: Boolean = false,
    var boardThumbnailUrl: String?,
    var categoryId: Long,
    var memberId: Long = 0L
) {
    companion object {
        fun of(board: Board): BoardInfoResponse {
            return BoardInfoResponse(board.id, board.title, board.content, board.isPrivate, board.boardThumbnailUrl, board.categoryId, board.memberId)
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