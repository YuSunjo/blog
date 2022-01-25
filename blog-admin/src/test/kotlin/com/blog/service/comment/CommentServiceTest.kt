package com.blog.service.comment

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
import com.blog.exception.ValidationException
import com.blog.service.TestUtils
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.util.*

@SpringBootTest
class CommentServiceTest(
    @Autowired
    private val commentService: CommentService,

    @Autowired
    private val commentRepository: CommentRepository,

    @Autowired
    private val boardRepository: BoardRepository,

    @Autowired
    private val categoryRepository: CategoryRepository,

    @Autowired
    private val memberRepository: MemberRepository
) {
    @AfterEach
    fun cleanUp() {
        commentRepository.deleteAll()
        boardRepository.deleteAll()
        categoryRepository.deleteAll()
        memberRepository.deleteAll()
    }

    @DisplayName("게시글에 첫 댓글을 등록한다.")
    @Test
    fun createComment() {
        // given
        val category = Category("java")
        categoryRepository.save(category)
        val board = Board("title", "content", false, null, category, 1L, 0, 0)
        boardRepository.save(board)

        val request = CreateCommentRequest("댓글입니다~", board.id, null)

        // when
        commentService.createComment(request, 2L)

        // then
        val commentList = commentRepository.findAll()
        assertThat(commentList).hasSize(1)
        TestUtils.assertComment(commentList[0], request.content, null, 0)
    }

    @DisplayName("게시글에 부모 댓글에 댓글을 등록한다.")
    @Test
    fun createComment2() {
        // given
        val category = Category("java")
        categoryRepository.save(category)
        val board = Board("title", "content", false, null, category, 1L, 0, 0)
        boardRepository.save(board)
        val comment = Comment(board.id, 2L, "댓글입니다~", null)
        commentRepository.save(comment)

        val request = CreateCommentRequest("댓글입니다~", board.id, comment.id)

        // when
        commentService.createComment(request, 2L)

        // then
        val commentList = commentRepository.findAll()
        assertThat(commentList).hasSize(2)
        TestUtils.assertComment(commentList[1], request.content, comment.id, 1)
    }

    @DisplayName("게시글에 부모 댓글의 댓글에 댓글을 달면 예외 발생")
    @Test
    fun createComment3() {
        // given
        val category = Category("java")
        categoryRepository.save(category)
        val board = Board("title", "content", false, null, category, 1L, 0, 0)
        boardRepository.save(board)
        val comment1 = Comment(board.id, 2L, "댓글입니다~", null)
        val comment2 = Comment(board.id, 2L, "대댓글입니다.", comment1, 1)
        commentRepository.saveAll(Arrays.asList(comment1, comment2))

        val request = CreateCommentRequest("댓글입니다~", board.id, comment2.id)

        // when & then
        assertThatThrownBy {
            commentService.createComment(request, 2L)
        }.isInstanceOf(ValidationException::class.java)
    }

    @DisplayName("게시글에 부모 댓글을 수정한다.")
    @Test
    fun updateComment() {
        // given
        val category = Category("java")
        categoryRepository.save(category)
        val board = Board("title", "content", false, null, category, 1L, 0, 0)
        boardRepository.save(board)
        val comment = Comment(board.id, 2L, "댓글입니다~", null)
        commentRepository.save(comment)

        val request = UpdateCommentRequest("댓글 수정입니다.", comment.id)

        // when
        commentService.updateComment(request, 2L)

        // then
        val commentList = commentRepository.findAll()
        assertThat(commentList).hasSize(1)
        TestUtils.assertComment(commentList[0], request.content, null, 0)
    }

    @DisplayName("게시글에 대댓글을 수정한다.")
    @Test
    fun updateComment2() {
        // given
        val category = Category("java")
        categoryRepository.save(category)
        val board = Board("title", "content", false, null, category, 1L, 0, 0)
        boardRepository.save(board)
        val comment1 = Comment(board.id, 2L, "댓글입니다~", null)
        val comment2 = Comment(board.id, 2L, "대댓글입니다.", comment1, 1)
        commentRepository.saveAll(Arrays.asList(comment1, comment2))

        val request = UpdateCommentRequest("댓글 수정입니다.", comment2.id)

        // when
        commentService.updateComment(request, 2L)

        // then
        val commentList = commentRepository.findAll()
        assertThat(commentList).hasSize(2)
        TestUtils.assertComment(commentList[1], request.content, comment1.id, 1)
    }

    @DisplayName("게시글을 불러온다 부모댓글만 달렸을 경우")
    @Test
    fun retrieveComment() {
        // given
        val member1 = Member("tnswh2023@naver.com", "password", null, Provider.LOCAL, Role.USER, "nickname1")
        val member2 = Member("tnswh2022@naver.com", "password", null, Provider.LOCAL, Role.USER, "nickname2")
        memberRepository.saveAll(Arrays.asList(member1, member2))
        val category = Category("java")
        categoryRepository.save(category)
        val board = Board("title", "content", false, null, category, 1L, 0, 0)
        boardRepository.save(board)
        val comment1 = Comment(board.id, member2.id, "댓글입니다~", null)
        val comment2 = Comment(board.id, member2.id, "댓글입니다2~", null)
        commentRepository.saveAll(Arrays.asList(comment1, comment2))

        // when
        val response = commentService.retrieveComment(board.id, 1L)

        // then
        assertThat(response).hasSize(2)
        assertThat(response[0].commentId).isEqualTo(comment1.id)
        assertThat(response[0].childCommentList).isEmpty()
        assertThat(response[0].memberInfoResponse?.id).isEqualTo(member2.id)
        assertThat(response[1].commentId).isEqualTo(comment2.id)
        assertThat(response[1].childCommentList).isEmpty()
        assertThat(response[1].memberInfoResponse?.id).isEqualTo(member2.id)
    }

    @DisplayName("게시글을 불러온다 부모댓글과 대댓글도 달렸을 경우")
    @Test
    fun retrieveComment2() {
        // given
        val member1 = Member("tnswh2023@naver.com", "password", null, Provider.LOCAL, Role.USER, "nickname1")
        val member2 = Member("tnswh2022@naver.com", "password", null, Provider.LOCAL, Role.USER, "nickname2")
        memberRepository.saveAll(Arrays.asList(member1, member2))
        val category = Category("java")
        categoryRepository.save(category)
        val board = Board("title", "content", false, null, category, 1L, 0, 0)
        boardRepository.save(board)
        val comment1 = Comment(board.id, member2.id, "댓글입니다~", null)
        val comment11 = Comment(board.id, member1.id, "대댓글입니다.", comment1, 1)
        val comment12 = Comment(board.id, member2.id, "대댓글2입니다.", comment1, 1)
        val comment2 = Comment(board.id, member2.id, "댓글입니다2~", null)
        val comment21 = Comment(board.id, member1.id, "대댓글입니다.", comment2, 1)
        commentRepository.saveAll(listOf(comment1, comment11, comment12, comment2, comment21))

        // when
        val response = commentService.retrieveComment(board.id, 1L)

        // then
        assertThat(response).hasSize(2)
        assertThat(response[0].commentId).isEqualTo(comment1.id)
        assertThat(response[0].childCommentList).hasSize(2)
        assertThat(response[0].memberInfoResponse?.id).isEqualTo(member2.id)
        assertThat(response[1].commentId).isEqualTo(comment2.id)
        assertThat(response[1].childCommentList).hasSize(1)
        assertThat(response[1].memberInfoResponse?.id).isEqualTo(member2.id)
    }

    @DisplayName("존재하지 않는 멤버의 댓글일 경우 memberInfoResponse 가 null 이다.")
    @Test
    fun retrieveComment3() {
        // given
        val category = Category("java")
        categoryRepository.save(category)
        val board = Board("title", "content", false, null, category, 1L, 0, 0)
        boardRepository.save(board)
        val comment1 = Comment(board.id, 2L, "댓글입니다~", null)
        val comment11 = Comment(board.id, 1L, "대댓글입니다.", comment1, 1)
        val comment12 = Comment(board.id, 2L, "대댓글2입니다.", comment1, 1)
        val comment2 = Comment(board.id, 2L, "댓글입니다2~", null)
        val comment21 = Comment(board.id, 1L, "대댓글입니다.", comment2, 1)
        commentRepository.saveAll(listOf(comment1, comment11, comment12, comment2, comment21))

        // when
        val response = commentService.retrieveComment(board.id, 1L)

        // then
        assertThat(response).hasSize(2)
        assertThat(response[0].commentId).isEqualTo(comment1.id)
        assertThat(response[0].childCommentList).hasSize(2)
        assertThat(response[0].memberInfoResponse?.id).isNull()
        assertThat(response[1].commentId).isEqualTo(comment2.id)
        assertThat(response[1].childCommentList).hasSize(1)
        assertThat(response[1].memberInfoResponse?.id).isNull()
    }

}