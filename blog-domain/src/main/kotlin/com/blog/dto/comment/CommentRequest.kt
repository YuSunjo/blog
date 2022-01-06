package com.blog.dto.comment

import javax.validation.constraints.NotBlank

data class CreateCommentRequest(
    @field:NotBlank
    var content: String,

    @field:NotBlank
    var boardId: Long,

    var parentCommentId: Long?
)

data class UpdateCommentRequest(
    @field:NotBlank
    var content: String,

    @field:NotBlank
    var commentId: Long,
)