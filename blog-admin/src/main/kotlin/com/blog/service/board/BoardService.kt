package com.blog.service.board

import com.blog.domain.board.Board
import com.blog.domain.board.BoardHashTag
import com.blog.domain.board.repository.BoardHashTagRepository
import com.blog.domain.board.repository.BoardRepository
import com.blog.domain.category.repository.CategoryRepository
import com.blog.dto.board.*
import com.blog.exception.NotFoundException
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class BoardService(
    private val boardRepository: BoardRepository,
    private val categoryRepository: CategoryRepository,
    private val boardHashTagRepository: BoardHashTagRepository,
) {

    @Transactional
    fun createBoard(request: CreateBoardRequest, memberId: Long): BoardInfoResponse {
        val category = (
            categoryRepository.findCategoryById(request.categoryId)
                ?: throw NotFoundException("존재하지 않는 카테고리 ${request.categoryId} 입니다.")
            )
        val board: Board = boardRepository.save(request.toEntity(memberId, category))
        board.addHashTag(request.hashTagList, memberId)
        return BoardInfoResponse.of(board)
    }

    @Transactional
    fun updateBoard(request: UpdateBoardRequest, memberId: Long): BoardInfoResponse {
        val board = (
            boardRepository.findBoardById(request.id)
                ?: throw NotFoundException("존재하지 않는 게시글 ${request.id} 입니다.")
            )
        val category = (
            categoryRepository.findCategoryById(request.categoryId)
                ?: throw NotFoundException("존재하지 않는 카테고리 ${request.categoryId} 입니다.")
            )
        val hashTagList = boardHashTagRepository.findHashTagByBoard(board)
        board.updateBoard(request, category)
        board.deleteHashTag(hashTagList)
        board.addHashTag(request.hashTagList, memberId)
        return BoardInfoResponse.of(board)
    }

    @Transactional(readOnly = true)
    fun getBoard(boardId: Long): BoardInfoResponse {
        val board = (
            boardRepository.findBoardById(boardId)
                ?: throw NotFoundException("존재하지 않는 게시글 $boardId 입니다.")
            )
        return BoardInfoResponse.of(board)
    }

    @Transactional(readOnly = true)
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

    @Transactional(readOnly = true)
    fun retrieveHashTag(): List<String> {
        return boardHashTagRepository.findDistinctHashTag()
    }

    fun searchAll(): String {
        return "elastic search"
    }
}