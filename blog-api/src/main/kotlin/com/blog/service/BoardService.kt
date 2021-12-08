package com.blog.service

import com.blog.domain.board.repository.BoardRepository
import com.blog.dto.board.BoardInfoListResponse
import com.blog.dto.board.BoardInfoResponse
import com.blog.dto.board.RetrieveBoardRequest
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.stream.Collectors

@Service
class BoardService(
    private val boardRepository: BoardRepository
) {
    @Transactional
    fun retrieveBoard(request: RetrieveBoardRequest): BoardInfoListResponse {
        val sort = request.sort ?: "id"
        val pageable: Pageable = PageRequest.of(request.page - 1, request.size, Sort.by(Sort.Direction.DESC, sort))
        val boardPagination = boardRepository.findBySearchingPagination(pageable, request.search)
        val boardList = boardPagination.stream().map {
            BoardInfoResponse.of(it)
        }.collect(Collectors.toList())
        return BoardInfoListResponse.of(boardList, boardPagination.totalPages)
    }

}