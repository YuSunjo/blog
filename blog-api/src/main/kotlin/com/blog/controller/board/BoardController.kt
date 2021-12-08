package com.blog.controller.board

import com.blog.ApiResponse
import com.blog.dto.board.BoardInfoListResponse
import com.blog.dto.board.RetrieveBoardRequest
import com.blog.service.BoardService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
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
}