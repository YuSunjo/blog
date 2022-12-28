package com.blog.dto.chat

import com.blog.domain.chat.ChatType
import com.blog.domain.chat.Chats
import java.time.LocalDateTime

data class ChatInfoResponse(
    var date: LocalDateTime?,
    var message: String,
    var name: String,
    var memberId: Long,
    var type: ChatType?,
) {

    companion object {
        fun of(chat: Chats): ChatInfoResponse {
            return ChatInfoResponse(chat.date, chat.message, chat.name, chat.memberId, chat.type)
        }
    }

}