package com.blog.domain.board

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
class Board(
        var title: String = "",
        var content: String = "",
        var isPrivate: Boolean,
        var boardThumbnailUrl: String?,
        var categoryId: Long,
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0L
}
