package com.blog.domain.board.elastic

import org.springframework.data.elasticsearch.core.ElasticsearchOperations
import org.springframework.data.elasticsearch.core.SearchHits
import org.springframework.data.elasticsearch.core.query.Criteria
import org.springframework.data.elasticsearch.core.query.CriteriaQuery
import org.springframework.stereotype.Repository
import java.util.stream.Collectors

@Repository
class boardSearchQueryRepository(
    private val operations: ElasticsearchOperations
) {
    fun boardSearchAll(): List<BoardDocument> {
        val query = CriteriaQuery(Criteria())
        val search: SearchHits<BoardDocument> = operations.search(query, BoardDocument::class.java)
        return search.stream().map {
            it.content
        }.collect(Collectors.toList())
    }
}