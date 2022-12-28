package com.blog.controller.chat

import com.blog.ApiResponse
import com.blog.config.argument.MemberId
import com.blog.config.auth.Member
import com.blog.dto.chat.ChatInfoResponse
import com.blog.service.chat.ChatService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class ChatController(
    private val chatService: ChatService
) {

    @Member
    @GetMapping("/api/v1/user/chat")
    fun retrieveCategory(@MemberId memberId: Long): ApiResponse<List<ChatInfoResponse>> {
        return ApiResponse.success(chatService.retrieveChat(memberId))
    }

}