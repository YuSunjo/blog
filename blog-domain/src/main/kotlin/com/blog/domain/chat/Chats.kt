package com.blog.domain.chat

import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime
import javax.persistence.Id

@Document(collection = "chats")
class Chats(
    var date: LocalDateTime? = null,
    var message: String = "",
    var name: String = "",
    var memberId: Long = 0L,
    var type: ChatType? = null
) {

    @Id
    var id: String = ""

}