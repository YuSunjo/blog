package com.blog.controller.board

import com.blog.ApiResponse
import com.blog.config.argument.MemberId
import com.blog.config.auth.Member
import com.blog.dto.board.BoardIdRequest
import com.blog.dto.board.BoardInfoListResponse
import com.blog.dto.board.RetrieveBoardRequest
import com.blog.service.BoardService
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
class BoardController(
        private val boardService: BoardService
) {
    @GetMapping("/api/v1/board/list")
    fun retrieveBoard(@Valid request: RetrieveBoardRequest): ApiResponse<BoardInfoListResponse> {
        boardService.retrieveBoard(request)
        return ApiResponse.success(boardService.retrieveBoard(request))
    }

    @Member
    @PostMapping("/api/v1/board/like")
    fun boardLike(@Valid @RequestBody request: BoardIdRequest, @MemberId memberId: Long): ApiResponse<String> {
        boardService.boardLike(request.boardId, memberId)
        return ApiResponse.OK
    }

    @Member
    @DeleteMapping("/api/v1/board/like")
    fun boardUnlike(@Valid @RequestBody request: BoardIdRequest, @MemberId memberId: Long): ApiResponse<String> {
        boardService.boardUnLike(request.boardId, memberId)
        return ApiResponse.OK
    }

}