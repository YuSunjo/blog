package com.blog.controller.chat

import com.blog.ApiResponse
import com.blog.config.auth.Member
import com.blog.dto.chat.ChatInfoResponse
import com.blog.service.chat.ChatService
import org.springframework.web.bind.annotation.*

@RestController
class ChatController(
    private val chatService: ChatService
) {

    @Member
    @GetMapping("/api/v1/chat/{memberId}")
    fun createBoard(@PathVariable memberId: Long): ApiResponse<List<ChatInfoResponse>> {
        return ApiResponse.success(chatService.retrieveChat(memberId))
    }

}