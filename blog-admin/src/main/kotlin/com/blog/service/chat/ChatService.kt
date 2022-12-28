package com.blog.service.chat

import com.blog.domain.chat.Chats
import com.blog.dto.chat.ChatInfoResponse
import org.springframework.data.domain.Sort
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.aggregation.Aggregation.*
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.stereotype.Service

@Service
class ChatService(
    private val mongoTemplate: MongoTemplate
) {
    /**
     * db.chats.aggregate([
    {$match: {userId : 10}},
    {$sort: {_id: -1}},
    {$limit: 5},
    ])
     */
    fun retrieveChat(memberId: Long): List<ChatInfoResponse> {
        val match = match(Criteria.where("memberId").`is`(memberId))
        val sort = sort(Sort.Direction.DESC, "_id")
        val limit = limit(5)
        val chatList: List<Chats> = mongoTemplate.aggregate(
            newAggregation(match, sort, limit),
            "chats",
            Chats::class.java
        ).mappedResults
        return chatList.asSequence().map { ChatInfoResponse.of(it) }.toList()
    }

}