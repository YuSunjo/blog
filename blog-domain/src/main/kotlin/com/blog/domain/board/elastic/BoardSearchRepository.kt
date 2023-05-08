package com.blog.domain.board.elastic

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository

interface BoardSearchRepository : ElasticsearchRepository<BoardDocument, Long> {
}