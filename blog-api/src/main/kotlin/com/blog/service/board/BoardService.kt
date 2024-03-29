package com.blog.service.board

import com.blog.domain.board.BoardHashTag
import com.blog.domain.board.repository.BoardHashTagRepository
import com.blog.domain.board.repository.BoardRepository
import com.blog.dto.board.BoardInfoListResponse
import com.blog.dto.board.BoardInfoResponse
import com.blog.dto.board.RetrieveBoardRequest
import com.blog.exception.NotFoundException
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class BoardService(
    private val boardRepository: BoardRepository,
    private val boardHashTagRepository: BoardHashTagRepository,
) {
    @Transactional
    fun retrieveBoard(request: RetrieveBoardRequest): BoardInfoListResponse {
        val pageable: Pageable = PageRequest.of(request.page - 1, request.size)
        var boardHashTagList = emptyList<BoardHashTag>()
        if (request.hashTag != null) {
            boardHashTagList = boardHashTagRepository.findByHashTag(request.hashTag)
        }
        val boardPagination = boardRepository.findBySearchingPagination(pageable, request.search, request.category, boardHashTagList)
        val boardList = boardPagination.asSequence().map {
            BoardInfoResponse.of(it)
        }.toList()
        return BoardInfoListResponse.of(boardList, boardPagination.totalPages)
    }

    @Transactional
    fun boardLike(boardId: Long, memberId: Long) {
        val board = (
            boardRepository.findBoardById(boardId)
                ?: throw NotFoundException("존재하지 않는 게시글 $boardId 입니다.")
            )
        board.boardAddLike(memberId)
    }

    @Transactional
    fun boardUnLike(boardId: Long, memberId: Long) {
        val board = (
            boardRepository.findBoardById(boardId)
                ?: throw NotFoundException("존재하지 않는 게시글 $boardId 입니다.")
            )
        board.boardUnLike(memberId)
    }

    @Transactional
    fun getBoard(boardId: Long): BoardInfoResponse {
        val board = (
            boardRepository.findBoardById(boardId)
                ?: throw NotFoundException("존재하지 않는 게시글 $boardId 입니다.")
            )
        return BoardInfoResponse.of(board)
    }

    @Transactional
    fun retrieveHashTag(): List<String> {
        return boardHashTagRepository.findDistinctHashTag()
    }
}