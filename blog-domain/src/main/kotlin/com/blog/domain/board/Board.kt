package com.blog.domain.board

import com.blog.dto.board.BoardHashTagInfoResponse
import com.blog.dto.board.UpdateBoardRequest
import com.blog.exception.ConflictException
import com.blog.exception.NotFoundException
import com.blog.exception.ValidationException
import java.util.*
import java.util.stream.Collectors
import javax.persistence.*
import kotlin.collections.ArrayList

@Entity
class Board(
        var title: String = "",
        var content: String = "",
        var isPrivate: Boolean = false,
        var boardThumbnailUrl: String?,
        var categoryId: Long,
        var memberId: Long = 0L,

        @OneToMany(mappedBy = "board", cascade = [CascadeType.ALL], orphanRemoval = true)
        private val boardLikeList: MutableList<BoardLike> = ArrayList(),

        @OneToMany(mappedBy = "board", cascade = [CascadeType.ALL], orphanRemoval = true)
        private val boardHashTagList: MutableList<BoardHashTag> = ArrayList()

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

    fun boardAddLike(memberId: Long) {
        this.findBoardLikeByMemberId(memberId)
            .ifPresent {
                throw ConflictException("이미 존재하는 멤버 ${memberId} 입니다.")
            }
        val boardLike = BoardLike.of(memberId, this)
        this.boardLikeList.add(boardLike)
    }

    private fun findBoardLikeByMemberId(memberId: Long): Optional<BoardLike> {
        return this.boardLikeList.stream().filter {
            it.findBoardLikeByMemberId(memberId)
        }.findFirst()
    }

    fun boardUnLike(memberId: Long) {
        val boardLike = this.findBoardLikeByMemberId(memberId)
            .orElseThrow {
                throw NotFoundException("멤버 ${memberId}가 좋아요 한 게시글이 없습니다.")
            }
        this.boardLikeList.remove(boardLike)
    }

    fun addHashTag(hashTagList: MutableList<String>, memberId: Long) {
        val boardHashTagList = hashTagList.stream().map {
            BoardHashTag.of(memberId, it, this)
        }.collect(Collectors.toList())
        this.boardHashTagList.addAll(boardHashTagList)
    }

    fun getBoardHashTagList(): MutableList<BoardHashTagInfoResponse> {
        return this.boardHashTagList.stream().map {
            BoardHashTagInfoResponse.of(it)
        }.collect(Collectors.toList())
    }

}
