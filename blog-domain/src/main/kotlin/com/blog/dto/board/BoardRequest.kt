package com.blog.dto.board

import com.blog.domain.board.Board
import javax.validation.constraints.Min
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

data class CreateBoardRequest(
    @field:NotBlank
    var title: String = "",
    @field:NotBlank
    var content: String = "",
    var isPrivate: Boolean,
    var boardThumbnailUrl: String?,
    @NotNull
    var categoryId: Long,
) {
    fun toEntity(memberId: Long): Board {
        return Board(title, content, isPrivate, boardThumbnailUrl, categoryId, memberId)
    }

}

data class UpdateBoardRequest(
    @field:NotNull
    var id: Long = 0L,
    @field:NotBlank
    var title: String = "",
    @field:NotBlank
    var content: String = "",
    var isPrivate: Boolean,
    var boardThumbnailUrl: String?,
    @NotNull
    var categoryId: Long,
)

data class RetrieveBoardRequest(
    @field:Min(1)
    var page: Int,
    @field:Min(1)
    var size: Int,
    var search: String?,
    var sort: String?
)