package com.blog.domain.board.repository

import com.blog.domain.board.BoardLike
import org.springframework.data.jpa.repository.JpaRepository

interface BoardLikeRepository : JpaRepository<BoardLike, Long>, BoardLikeRepositoryCustom {
}