package com.blog.domain.comment

import com.blog.domain.BaseTimeEntity
import com.blog.exception.ValidationException
import javax.persistence.*
import javax.persistence.FetchType.LAZY

@Entity
class Comment(

    @Column(nullable = false)
    var boardId: Long,

    @Column(nullable = false)
    var memberId: Long,

    @Column(nullable = false)
    var content: String,

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "parent_id")
    var parentComment: Comment?,

    @Column(nullable = false)
    var depth: Int = 0,

    ): BaseTimeEntity() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0L

    @OneToMany(mappedBy = "parentComment", cascade = [CascadeType.ALL])
    val childComments: MutableList<Comment> = ArrayList()

    fun addChildComment(memberId: Long, content: String, boardId: Long): Comment {
        if (this.parentComment != null || this.depth == 1) {
            throw ValidationException("댓글은 1depth 까지 입니다.")
        }
        val childComment = Comment(boardId, memberId, content, this, this.depth + 1)
        this.childComments.add(childComment)
        return childComment
    }

    fun updateContent(content: String) {
        this.content = content
    }

    companion object {
        fun newRootComment(boardId: Long, content: String, memberId: Long): Comment {
            return Comment(boardId, memberId, content, null, 0)
        }
    }

}