package com.blog.domain.board

import javax.persistence.*

@Entity
class BoardHashTag(

    @Column(nullable = false)
    var memberId: Long,

    @Column(nullable = false)
    var hashTag: String,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id", nullable = false)
    var board: Board

) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0L

    companion object {
        fun of(memberId: Long, hashTag: String, board: Board): BoardHashTag {
            return BoardHashTag(memberId, hashTag, board)
        }
    }
}