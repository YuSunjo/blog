package com.blog.controller.comment

import com.blog.ApiResponse
import com.blog.config.argument.MemberId
import com.blog.config.auth.Member
import com.blog.dto.comment.CreateCommentRequest
import com.blog.dto.comment.UpdateCommentRequest
import com.blog.dto.comment.response.CommentInfoResponse
import com.blog.service.comment.CommentService
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
class CommentController(
    private val commentService: CommentService
) {

    @Member
    @PostMapping("api/v1/comment")
    fun createComment(@RequestBody @Valid request: CreateCommentRequest, @MemberId memberId: Long): ApiResponse<String> {
        commentService.createComment(request, memberId)
        return ApiResponse.OK
    }

    @Member
    @PutMapping("api/v1/comment")
    fun updateComment(@RequestBody @Valid request: UpdateCommentRequest, @MemberId memberId: Long): ApiResponse<String> {
        commentService.updateComment(request, memberId)
        return ApiResponse.OK
    }

    @GetMapping("api/v1/comment/{boardId}")
    fun retrieveComment(@PathVariable boardId: Long): ApiResponse<List<CommentInfoResponse>> {
        return ApiResponse.success(commentService.retrieveComment(boardId))
    }
}