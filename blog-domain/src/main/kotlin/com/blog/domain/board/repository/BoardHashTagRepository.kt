package com.blog.domain.board.repository

import com.blog.domain.board.BoardHashTag
import org.springframework.data.jpa.repository.JpaRepository

interface BoardHashTagRepository: JpaRepository<BoardHashTag, Long>, BoardHashTagRepositoryCustom {
}