package com.blog.service.comment

import com.blog.domain.board.repository.BoardRepository
import com.blog.domain.comment.Comment
import com.blog.domain.comment.repository.CommentRepository
import com.blog.domain.member.Member
import com.blog.domain.member.repository.MemberRepository
import com.blog.dto.comment.CreateCommentRequest
import com.blog.dto.comment.UpdateCommentRequest
import com.blog.dto.comment.response.CommentInfoResponse
import com.blog.exception.NotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.stream.Collectors

@Service
class CommentService(
    private val boardRepository: BoardRepository,
    private val commentRepository: CommentRepository,
    private val memberRepository: MemberRepository
) {
    @Transactional
    fun createComment(request: CreateCommentRequest, memberId: Long) {
        val board = (boardRepository.findBoardById(request.boardId)
            ?: throw NotFoundException("존재하지 않는 게시글 ${request.boardId} 입니다."))
        if (request.parentCommentId != null) {
            val boardComment = (commentRepository.findCommentById(request.parentCommentId!!)
                ?: throw NotFoundException("존재하지 않는 부모 댓글 ${request.parentCommentId} 입니다."))
            boardComment.addChildComment(memberId, request.content, request.boardId)
            board.incrementLikeCount()
        }
        commentRepository.save(Comment.newRootComment(request.boardId, request.content, memberId))
        board.incrementLikeCount()
    }

    @Transactional
    fun updateComment(request: UpdateCommentRequest, memberId: Long) {
        val comment = (commentRepository.findCommentById(request.commentId)
            ?: throw NotFoundException("존재하지 않는 댓글 ${request.commentId} 입니다."))
        comment.updateContent(request.content)
    }

    @Transactional
    fun retrieveComment(boardId: Long, memberId: Long): List<CommentInfoResponse> {
        (boardRepository.findBoardById(boardId)
            ?: throw NotFoundException("존재하지 않는 게시글 $boardId 입니다."))
        val boardCommentList = commentRepository.findCommentByBoardId(boardId)
        val commentMemberIds = boardCommentList.stream().map { it.memberId }.collect(Collectors.toList())
        val memberMap: Map<Long, Member> = memberRepository.findAllByIds(commentMemberIds).associateBy { it.id }
        return boardCommentList.stream().map {
            CommentInfoResponse.of(it, memberMap)
        }.collect(Collectors.toList())
    }

}