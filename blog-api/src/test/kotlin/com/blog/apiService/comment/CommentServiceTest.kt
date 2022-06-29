package com.blog.apiService.comment

import com.blog.TestUtils
import com.blog.domain.board.Board
import com.blog.domain.board.repository.BoardRepository
import com.blog.domain.category.Category
import com.blog.domain.category.repository.CategoryRepository
import com.blog.domain.comment.Comment
import com.blog.domain.comment.repository.CommentRepository
import com.blog.domain.member.Member
import com.blog.domain.member.Provider
import com.blog.domain.member.Role
import com.blog.domain.member.repository.MemberRepository
import com.blog.dto.comment.CreateCommentRequest
import com.blog.dto.comment.UpdateCommentRequest
import com.blog.exception.NotFoundException
import com.blog.service.comment.CommentService
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.TestConstructor

@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@SpringBootTest
class CommentServiceTest(
    private val commentService: CommentService,
    private val commentRepository: CommentRepository,
    private val categoryRepository: CategoryRepository,
    private val memberRepository: MemberRepository,
    private val boardRepository: BoardRepository,
) {

    @AfterEach
    fun cleanUp() {
        boardRepository.deleteAll()
        categoryRepository.deleteAll()
        commentRepository.deleteAll()
        memberRepository.deleteAll()
    }

    @DisplayName("parentComment 가 null 이면 댓글이 달리고 게시글의 댓글 개수가 1이된다.")
    @Test
    fun createComment_1() {
        // given
        val category = Category("java")
        categoryRepository.save(category)
        val board = Board("title", "content", false, "image.png", category, 1L, 0, 0)
        boardRepository.save(board)

        val request = CreateCommentRequest("코멘트 입니다.", board.id, null)

        // when
        commentService.createComment(request, 1L)

        // then
        val commentList = commentRepository.findAll()
        val boardList = boardRepository.findAll()
        assertThat(commentList).hasSize(1)
        assertThat(boardList[0].commentCount).isEqualTo(1)
    }

    @DisplayName("대댓글을 달면 parentComment 가 존재하고 댓글 개수가 2개가 된다.")
    @Test
    fun createComment_2() {
        // given
        val category = Category("java")
        categoryRepository.save(category)
        val board = Board("title", "content", false, "image.png", category, 1L, 0, 0)
        board.incrementCommentCount()
        boardRepository.save(board)
        val comment = Comment(board.id, 1L, "댓글입니다.", null)
        commentRepository.save(comment)

        val request = CreateCommentRequest("코멘트 입니다.", board.id, comment.id)

        // when
        commentService.createComment(request, 1L)

        // then
        val commentList = commentRepository.findAll()
        val boardList = boardRepository.findAll()
        assertThat(commentList).hasSize(2)
        assertThat(boardList[0].commentCount).isEqualTo(2)
        TestUtils.assertComment(commentList[1], request.content, comment.id, 1)
    }

    @DisplayName("boardId 가 존재하지 않는 글에 댓글 요청을 하면 예외처리 한다.")
    @Test
    fun createComment_error_1() {
        // given
        val request = CreateCommentRequest("코멘트 입니다.", 1L, null)

        // when & then
        assertThatThrownBy {
            commentService.createComment(request, 1L)
        }.isInstanceOf(NotFoundException::class.java)
    }

    @DisplayName("존재하지 않는 parentComment에 댓글을 달려고 하면 예외처리한다.")
    @Test
    fun createComment_error_2() {
        // given
        val category = Category("java")
        categoryRepository.save(category)
        val board = Board("title", "content", false, "image.png", category, 1L, 0, 0)
        boardRepository.save(board)
        val request = CreateCommentRequest("코멘트 입니다.", board.id, 1L)

        // when & then
        assertThatThrownBy {
            commentService.createComment(request, 1L)
        }.isInstanceOf(NotFoundException::class.java)
    }

    @DisplayName("댓글 수정하기")
    @Test
    fun updateComment_1() {
        // given
        val category = Category("java")
        categoryRepository.save(category)
        val board = Board("title", "content", false, "image.png", category, 1L, 0, 0)
        boardRepository.save(board)
        val comment = Comment(board.id, 1L, "댓글입니다.", null)
        commentRepository.save(comment)

        val request = UpdateCommentRequest("댓글 수정할게요", comment.id)

        // when
        commentService.updateComment(request, 1L)

        // then
        val commentList = commentRepository.findAll()
        assertThat(commentList[0].content).isEqualTo(request.content)
    }

    @DisplayName("댓글 불러오기")
    @Test
    fun retrieveComment_1() {
        // given
        val member = Member("tnswh2023@naver.com", "11", null, Provider.LOCAL, Role.USER, "tnswh", null)
        memberRepository.save(member)
        val category = Category("java")
        categoryRepository.save(category)
        val board = Board("title", "content", false, "image.png", category, member.id, 0, 0)
        boardRepository.save(board)
        val comment1 = Comment(board.id, member.id, "댓글입니다.", null)
        val comment1_1 = Comment(board.id, member.id, "댓글입니다.", comment1)
        commentRepository.saveAll(listOf(comment1, comment1_1))

        // when
        val retrieveComment = commentService.retrieveComment(board.id)

        // then
        assertThat(retrieveComment[0].commentId).isEqualTo(comment1.id)
        assertThat(retrieveComment[0].memberInfoResponse?.id).isEqualTo(member.id)
        assertThat(retrieveComment[0].childCommentList).hasSize(1)
        assertThat(retrieveComment[0].childCommentList[0].commentId).isEqualTo(comment1_1.id)
    }
}