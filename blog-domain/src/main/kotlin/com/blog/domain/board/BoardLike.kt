package com.blog.domain.board

import com.blog.domain.BaseTimeEntity
import javax.persistence.*

@Entity
class BoardLike(
    var memberId: Long,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id", nullable = false)
    var board: Board
): BaseTimeEntity() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0L

    fun findBoardLikeByMemberId(memberId: Long): Boolean {
        return this.memberId == memberId
    }

    companion object {
        fun of(memberId: Long, board: Board): BoardLike {
            return BoardLike(memberId, board)
        }
    }

}