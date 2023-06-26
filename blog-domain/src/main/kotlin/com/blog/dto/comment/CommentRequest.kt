package com.blog.dto.comment

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

data class CreateCommentRequest(
    @field:NotBlank
    var content: String,

    @field:NotNull
    var boardId: Long,

    var parentCommentId: Long?
) {

    fun hasParentComment(): Boolean {
        return parentCommentId != null
    }
}

data class UpdateCommentRequest(
    @field:NotBlank
    var content: String,

    @field:NotNull
    var commentId: Long,
)