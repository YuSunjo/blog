package com.blog.service.comment

import com.blog.domain.comment.repository.CommentRepository
import org.springframework.stereotype.Service

@Service
class CommentService(
    private val commentRepository: CommentRepository
) {
}