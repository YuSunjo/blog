package com.blog.domain.board.repository

import com.blog.domain.board.Board
import com.blog.domain.board.BoardHashTag

interface BoardHashTagRepositoryCustom {

    fun findHashTagByBoard(board: Board): List<BoardHashTag>

}