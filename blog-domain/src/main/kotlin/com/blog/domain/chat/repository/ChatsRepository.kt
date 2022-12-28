package com.blog.domain.chat.repository

import com.blog.domain.chat.Chats
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface ChatsRepository: MongoRepository<Chats, String>