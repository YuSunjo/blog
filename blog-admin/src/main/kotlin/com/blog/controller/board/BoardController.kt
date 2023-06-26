package com.blog.controller.board

import com.blog.ApiResponse
import com.blog.config.argument.MemberId
import com.blog.config.auth.Member
import com.blog.dto.board.*
import com.blog.service.board.BoardService
import io.swagger.annotations.ApiOperation
import org.springframework.web.bind.annotation.*
import jakarta.validation.Valid

@RestController
class BoardController(
    private val boardService: BoardService
) {
    @ApiOperation(value = "게시글 작성", notes = "게시글 작성")
    @Member
    @PostMapping("/api/v1/board")
    fun createBoard(@RequestBody @Valid request: CreateBoardRequest, @MemberId memberId: Long): ApiResponse<BoardInfoResponse> {
        return ApiResponse.success(boardService.createBoard(request, memberId))
    }

    @Member
    @PutMapping("/api/v1/board")
    fun updateBoard(@RequestBody @Valid request: UpdateBoardRequest, @MemberId memberId: Long): ApiResponse<BoardInfoResponse> {
        return ApiResponse.success(boardService.updateBoard(request, memberId))
    }

    @Member
    @GetMapping("/api/v1/board/{boardId}")
    fun getBoard(@PathVariable boardId: Long, @MemberId memberId: Long): ApiResponse<BoardInfoResponse> {
        return ApiResponse.success(boardService.getBoard(boardId))
    }

    @Member
    @GetMapping("/api/v1/board/list")
    fun retrieveBoard(@Valid request: RetrieveBoardRequest): ApiResponse<BoardInfoListResponse> {
        return ApiResponse.success(boardService.retrieveBoard(request))
    }

    @Member
    @GetMapping("/api/v1/hashTag")
    fun retrieveHashTag(): ApiResponse<List<String>> {
        return ApiResponse.success(boardService.retrieveHashTag())
    }

    @GetMapping("api/v1/search")
    fun searchAll(): ApiResponse<String> {
        return ApiResponse.success(boardService.searchAll())
    }
}