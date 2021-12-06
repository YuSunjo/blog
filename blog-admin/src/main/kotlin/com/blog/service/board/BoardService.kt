package com.blog.service.board

import com.blog.domain.board.Board
import com.blog.domain.board.repository.BoardRepository
import com.blog.dto.board.*
import com.blog.exception.NotFoundException
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.domain.Sort.Direction.DESC
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.stream.Collectors

@Service
class BoardService(
    private val boardRepository: BoardRepository
) {

    @Transactional
    fun createBoard(request: CreateBoardRequest, memberId: Long): BoardInfoResponse {
        val board: Board = boardRepository.save(request.toEntity(memberId))
        return BoardInfoResponse.of(board)
    }

    @Transactional
    fun updateBoard(request: UpdateBoardRequest, memberId: Long): BoardInfoResponse {
        val board = (boardRepository.findBoardById(request.id)
            ?: throw NotFoundException("존재하지 않는 게시글 ${request.id} 입니다."))
        board.updateBoard(request)
        return BoardInfoResponse.of(board)
    }

    @Transactional(readOnly = true)
    fun getBoard(boardId: Long): BoardInfoResponse {
        val board = (boardRepository.findBoardById(boardId)
            ?: throw NotFoundException("존재하지 않는 게시글 ${boardId} 입니다."))
        return BoardInfoResponse.of(board)
    }

    @Transactional(readOnly = true)
    fun retrieveBoard(request: RetrieveBoardRequest): BoardInfoListResponse {
        val sort = request.sort ?: "id"
        val pageable: Pageable = PageRequest.of(request.page - 1, request.size, Sort.by(DESC, sort))
        val boardPagination = boardRepository.findBySearchingPagination(pageable, request.search)
        val boardList = boardPagination.stream().map {
            BoardInfoResponse.of(it)
        }.collect(Collectors.toList())
        return BoardInfoListResponse.of(boardList, boardPagination.totalPages)
    }

}