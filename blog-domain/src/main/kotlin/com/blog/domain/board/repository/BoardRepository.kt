package com.blog.domain.board.repository

import com.blog.domain.board.Board
import org.springframework.data.jpa.repository.JpaRepository

interface BoardRepository: JpaRepository<Board, Long> {
}