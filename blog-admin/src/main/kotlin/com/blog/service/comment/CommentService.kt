package com.blog.service.comment

import com.blog.domain.admin.repository.AdminRepository
import com.blog.domain.board.repository.BoardRepository
import com.blog.domain.comment.Comment
import com.blog.domain.comment.repository.CommentRepository
import com.blog.dto.adminMember.AdminInfoResponse
import com.blog.dto.comment.CreateCommentRequest
import com.blog.dto.comment.UpdateCommentRequest
import com.blog.dto.comment.response.CommentInfoResponse
import com.blog.exception.NotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.stream.Collectors

@Service
class CommentService(
    private val adminRepository: AdminRepository,
    private val boardRepository: BoardRepository,
    private val commentRepository: CommentRepository
) {
    @Transactional
    fun createComment(request: CreateCommentRequest, memberId: Long): CommentInfoResponse {
        println("sdfsdfsdf ")
        val board = (boardRepository.findBoardById(request.boardId)
            ?: throw NotFoundException("존재하지 않는 게시글 ${request.boardId} 입니다."))
        val admin = (adminRepository.findAdminById(memberId)
            ?: throw NotFoundException("존재하지 않는 멤버 ${memberId} 입니다."))
        if (request.parentCommentId != null) {
            val boardComment = (commentRepository.findCommentById(request.parentCommentId!!)
                ?: throw NotFoundException("존재하지 않는 부모 댓글 ${request.parentCommentId} 입니다."))
            val boardChildComment = boardComment.addChildComment(memberId, request.content, request.boardId)
            board.incrementLikeCount()
            return CommentInfoResponse.of(boardChildComment, AdminInfoResponse.of(admin))
        }
        val boardComment = commentRepository.save(Comment.newRootComment(request.boardId, request.content, memberId))
        board.incrementLikeCount()
        return CommentInfoResponse.of(boardComment, AdminInfoResponse.of(admin))
    }

    @Transactional
    fun updateComment(request: UpdateCommentRequest, memberId: Long): CommentInfoResponse {
        val comment = (commentRepository.findCommentById(request.commentId)
            ?: throw NotFoundException("존재하지 않는 댓글 ${request.commentId} 입니다."))
        val admin = (adminRepository.findAdminById(memberId)
            ?: throw NotFoundException("존재하지 않는 멤버 ${memberId} 입니다."))
        comment.updateContent(request.content)
        return CommentInfoResponse.of(comment, AdminInfoResponse.of(admin))
    }

    @Transactional
    fun retrieveComment(boardId: Long, memberId: Long): List<CommentInfoResponse> {
        val board = (boardRepository.findBoardById(boardId)
            ?: throw NotFoundException("존재하지 않는 게시글 ${boardId} 입니다."))
        val admin = (adminRepository.findAdminById(memberId)
            ?: throw NotFoundException("존재하지 않는 멤버 ${memberId} 입니다."))
        val boardCommentList = commentRepository.findCommentByBoardId(boardId)
        return boardCommentList.stream().map {
            CommentInfoResponse.of(it, AdminInfoResponse.of(admin))
        }.collect(Collectors.toList())
    }

}