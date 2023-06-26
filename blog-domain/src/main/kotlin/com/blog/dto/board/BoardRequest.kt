package com.blog.dto.board

import com.blog.domain.board.Board
import com.blog.domain.category.Category
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

data class CreateBoardRequest(
    @field:NotBlank
    var title: String = "",
    @field:NotBlank
    var content: String = "",
    var isPrivate: Boolean,
    var boardThumbnailUrl: String?,
    @NotNull
    var categoryId: Long,

    var hashTagList: MutableList<String>
) {
    fun toEntity(memberId: Long, category: Category): Board {
        return Board(title, content, isPrivate, boardThumbnailUrl, category, memberId)
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
    var hashTagList: MutableList<String>
)

data class RetrieveBoardRequest(
    @field:Min(1)
    var page: Int,
    @field:Min(1)
    var size: Int,
    var search: String?,
    var category: String?,
    var hashTag: String?
)

data class BoardIdRequest(
    @field:NotNull
    var boardId: Long
)