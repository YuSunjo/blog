package com.blog.domain.board.repository

import com.blog.domain.board.Board
import com.blog.domain.board.BoardHashTag
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface BoardRepositoryCustom {
    fun findBoardById(id: Long): Board?

    fun findBySearchingPagination(pageable: Pageable, search: String?): Page<Board>

}