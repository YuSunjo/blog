package com.blog.domain.comment.repository

import com.blog.domain.comment.Comment
import org.springframework.data.jpa.repository.JpaRepository

interface CommentRepository : JpaRepository<Comment, Long>, CommentRepositoryCustom