package com.blog.controller.board

import com.blog.ApiResponse
import com.blog.config.argument.MemberId
import com.blog.config.auth.Member
import com.blog.dto.board.*
import com.blog.service.board.BoardService
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
class BoardController(
    private val boardService: BoardService
) {
    @GetMapping("/api/v1/board/list")
    fun retrieveBoard(@Valid request: RetrieveBoardRequest): ApiResponse<BoardInfoListResponse> {
        return ApiResponse.success(boardService.retrieveBoard(request))
    }

    @GetMapping("/api/v1/board/{boardId}")
    fun getBoard(@PathVariable boardId: Long): ApiResponse<BoardInfoResponse> {
        return ApiResponse.success(boardService.getBoard(boardId))
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

    @GetMapping("/api/v1/hashTag")
    fun retrieveHashTag(): ApiResponse<List<String>> {
        return ApiResponse.success(boardService.retrieveHashTag())
    }
}