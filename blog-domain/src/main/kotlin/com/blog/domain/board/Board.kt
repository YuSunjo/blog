package com.blog.domain.board

import com.blog.dto.board.UpdateBoardRequest
import javax.persistence.*

@Entity
class Board(
        var title: String = "",
        var content: String = "",
        var isPrivate: Boolean = false,
        var boardThumbnailUrl: String?,
        var categoryId: Long,
        var memberId: Long = 0L,

        @OneToMany(mappedBy = "board", cascade = [CascadeType.ALL], orphanRemoval = true)
        private val boardLikeList: List<BoardLike>? = ArrayList()
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0L

    fun updateBoard(request: UpdateBoardRequest) {
        this.title = request.title
        this.content = request.content
        this.isPrivate = request.isPrivate
        this.boardThumbnailUrl = request.boardThumbnailUrl
        this.categoryId = request.categoryId
    }

}
