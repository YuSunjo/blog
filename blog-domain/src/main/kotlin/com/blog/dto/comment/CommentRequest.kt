package com.blog.dto.comment

import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

data class CreateCommentRequest(
    @field:NotBlank
    var content: String,

    @field:NotNull
    var boardId: Long,

    var parentCommentId: Long?
)

data class UpdateCommentRequest(
    @field:NotBlank
    var content: String,

    @field:NotNull
    var commentId: Long,
)