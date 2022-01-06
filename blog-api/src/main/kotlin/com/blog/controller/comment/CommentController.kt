package com.blog.controller.comment

import com.blog.service.comment.CommentService
import org.springframework.web.bind.annotation.RestController

@RestController
class CommentController(
    private val commentService: CommentService
) {
    // api 에서는 get 만


}