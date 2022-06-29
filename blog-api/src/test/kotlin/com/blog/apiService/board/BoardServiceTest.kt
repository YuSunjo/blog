package com.blog.apiService.board

import com.blog.TestUtils
import com.blog.domain.board.Board
import com.blog.domain.board.repository.BoardHashTagRepository
import com.blog.domain.board.repository.BoardLikeRepository
import com.blog.domain.board.repository.BoardRepository
import com.blog.domain.category.Category
import com.blog.domain.category.repository.CategoryRepository
import com.blog.dto.board.RetrieveBoardRequest
import com.blog.exception.ConflictException
import com.blog.exception.NotFoundException
import com.blog.service.board.BoardService
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.TestConstructor
import java.util.*

@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@SpringBootTest
class BoardServiceTest(
    private val boardRepository: BoardRepository,
    private val boardHashTagRepository: BoardHashTagRepository,
    private val boardLikeRepository: BoardLikeRepository,
    private val categoryRepository: CategoryRepository,
    private val boardService: BoardService
) {

    @AfterEach
    fun clean() {
        boardRepository.deleteAll()
        boardHashTagRepository.deleteAll()
        boardLikeRepository.deleteAll()
        categoryRepository.deleteAll()
    }

    @DisplayName("저장된 해시태그 불러오기")
    @Test
    fun retrieveHashTag() {
        // given
        val category = categoryRepository.save(Category("gogo"))

        val hashTagList = mutableListOf("자바", "코틀린", "살자")
        val board = Board("title", "content", true, null, category, 1L, 0, 0)
        board.addHashTag(hashTagList, 1L)
        boardRepository.save(board)

        // when
        val retrieveHashTag = boardService.retrieveHashTag()

        // then
        assertThat(retrieveHashTag).hasSize(3)
    }

    @DisplayName("게시글 좋아요 눌렀을 때 좋아요 개수가 1이된다.")
    @Test
    fun boardLike_1() {
        // given
        val category = Category("java")
        categoryRepository.save(category)
        val board = Board("title", "content", false, "image.png", category, 1L, 0, 0)
        boardRepository.save(board)

        // when
        boardService.boardLike(board.id, 1L)

        // then
        val boardList = boardRepository.findAll()
        assertThat(boardList[0].likeCount).isEqualTo(1)
    }

    @DisplayName("게시글 좋아요 눌렀을 때 db에 저장된다.")
    @Test
    fun boardLike_2() {
        // given
        val category = Category("java")
        categoryRepository.save(category)
        val board = Board("title", "content", false, "image.png", category, 1L, 0, 0)
        boardRepository.save(board)

        // when
        boardService.boardLike(board.id, 1L)

        // then
        val boardLikeList = boardLikeRepository.findAll()
        assertThat(boardLikeList).hasSize(1)
    }

    @DisplayName("게시글 좋아요 눌렀을 때 이미 있는 유저가 좋아요를 했을 때 예외처리한다.")
    @Test
    fun boardLike_error_1() {
        // given
        val category = Category("java")
        categoryRepository.save(category)
        val board = Board("title", "content", false, "image.png", category, 1L, 0, 0)
        board.boardAddLike(1L)
        boardRepository.save(board)

        // when & then
        assertThatThrownBy { boardService.boardLike(board.id, 1L) }.isInstanceOf(ConflictException::class.java)
    }

    @DisplayName("게시글 좋아요 1개가 있을 경우 취소 눌렀을 때 게시글 좋아요 개수는 0개가 된다.")
    @Test
    fun boardUnLike_1() {
        // given
        val category = Category("java")
        categoryRepository.save(category)
        val board = Board("title", "content", false, "image.png", category, 1L, 0, 0)
        board.boardAddLike(1L)
        boardRepository.save(board)

        // when
        boardService.boardUnLike(board.id, 1L)

        // then
        val boardList = boardRepository.findAll()
        assertThat(boardList[0].likeCount).isEqualTo(0)
    }

    @DisplayName("게시글 좋아요 취소 눌렀을 때 db에서 삭제된다.")
    @Test
    fun boardUnLike_2() {
        // given
        val category = Category("java")
        categoryRepository.save(category)
        val board = Board("title", "content", false, "image.png", category, 1L, 0, 0)
        board.boardAddLike(1L)
        boardRepository.save(board)

        // when
        boardService.boardUnLike(board.id, 1L)

        // then
        val boardLikeList = boardLikeRepository.findAll()
        assertThat(boardLikeList).hasSize(0)
    }

    @DisplayName("게시글 좋아요 취소 눌렀을 때 없는 유저가 좋아요한다면 예외처리한다.")
    @Test
    fun boardUnLike_error_1() {
        // given
        val category = Category("java")
        categoryRepository.save(category)
        val board = Board("title", "content", false, "image.png", category, 1L, 0, 0)
        boardRepository.save(board)

        // when & then
        assertThatThrownBy { boardService.boardUnLike(board.id, 1L) }.isInstanceOf(NotFoundException::class.java)
    }

    @DisplayName("게시글 리스트 불러오기")
    @Test
    fun retrieveBoard() {
        // given
        val category = categoryRepository.save(Category("gogo"))
        val board1 = Board("title1", "content1", false, "url1", category, 1L, 0, 0)
        board1.addHashTag(Arrays.asList("1", "2"), 1)
        val board2 = Board("title2", "content2", false, "url2", category, 1L, 0, 0)
        board2.addHashTag(Arrays.asList("1", "2"), 1)
        val board3 = Board("title3", "content3", false, "url3", category, 1L, 0, 0)
        board3.addHashTag(Arrays.asList("1", "2"), 1)

        boardRepository.saveAll(listOf(board1, board2, board3))
        val request = RetrieveBoardRequest(1, 3, null, null, null)

        // when
        val response = boardService.retrieveBoard(request)

        // then
        val boardList = boardRepository.findAll()
        assertThat(boardList).hasSize(3)
        assertThat(response.boardList).hasSize(3)
        assertThat(response.totalPage).isEqualTo(1)
        TestUtils.assertBoardInfoResponse(response.boardList[0], board3.title, board3.content, board3.boardThumbnailUrl)
        TestUtils.assertBoardInfoResponse(response.boardList[1], board2.title, board2.content, board2.boardThumbnailUrl)
        TestUtils.assertBoardInfoResponse(response.boardList[2], board1.title, board1.content, board1.boardThumbnailUrl)
    }

    @DisplayName("게시글 리스트 불러오기_2 페이지랑 사이즈 잘 불러오는지")
    @Test
    fun retrieveBoard_1() {
        // given
        val category = categoryRepository.save(Category("gogo"))
        val board1 = Board("title1", "content1", false, "url1", category, 1L, 0, 0)
        board1.addHashTag(Arrays.asList("1", "2"), 1)
        val board2 = Board("title2", "content2", false, "url2", category, 1L, 0, 0)
        board2.addHashTag(Arrays.asList("1", "2"), 1)
        val board3 = Board("title3", "content3", false, "url3", category, 1L, 0, 0)
        board3.addHashTag(Arrays.asList("1", "2"), 1)
        val board4 = Board("title4", "content3", false, "url3", category, 1L, 0, 0)
        board4.addHashTag(Arrays.asList("1", "2"), 1)
        val board5 = Board("title5", "content3", false, "url3", category, 1L, 0, 0)
        board5.addHashTag(Arrays.asList("1", "2"), 1)

        boardRepository.saveAll(listOf(board1, board2, board3, board4, board5))
        val request = RetrieveBoardRequest(1, 2, null, null, null)

        // when
        val response = boardService.retrieveBoard(request)

        // then
        val boardList = boardRepository.findAll()
        assertThat(boardList).hasSize(5)
        assertThat(response.boardList).hasSize(2)
        assertThat(response.totalPage).isEqualTo(3)
        TestUtils.assertBoardInfoResponse(response.boardList[0], board5.title, board5.content, board5.boardThumbnailUrl)
        TestUtils.assertBoardInfoResponse(response.boardList[1], board4.title, board4.content, board4.boardThumbnailUrl)
    }

    @DisplayName("게시글 리스트 불러오기 2번째 페이지랑 사이즈 잘 불러오는지")
    @Test
    fun retrieveBoard_1_1() {
        // given
        val category = categoryRepository.save(Category("gogo"))
        val board1 = Board("title1", "content1", false, "url1", category, 1L, 0, 0)
        board1.addHashTag(Arrays.asList("1", "2"), 1)
        val board2 = Board("title2", "content2", false, "url2", category, 1L, 0, 0)
        board2.addHashTag(Arrays.asList("1", "2"), 1)
        val board3 = Board("title3", "content3", false, "url3", category, 1L, 0, 0)
        board3.addHashTag(Arrays.asList("1", "2"), 1)
        val board4 = Board("title4", "content3", false, "url3", category, 1L, 0, 0)
        board3.addHashTag(Arrays.asList("1", "2"), 1)
        val board5 = Board("title5", "content3", false, "url3", category, 1L, 0, 0)
        board3.addHashTag(Arrays.asList("1", "2"), 1)

        boardRepository.saveAll(listOf(board1, board2, board3, board4, board5))
        val request = RetrieveBoardRequest(2, 2, null, null, null)

        // when
        val response = boardService.retrieveBoard(request)

        // then
        val boardList = boardRepository.findAll()
        assertThat(boardList).hasSize(5)
        assertThat(response.boardList).hasSize(2)
        assertThat(response.totalPage).isEqualTo(3)
        TestUtils.assertBoardInfoResponse(response.boardList[0], board3.title, board3.content, board3.boardThumbnailUrl)
        TestUtils.assertBoardInfoResponse(response.boardList[1], board2.title, board2.content, board2.boardThumbnailUrl)
    }

    @DisplayName("category를 통해서 게시글 불러오기")
    @Test
    fun retrieveBoard_2() {
        // given
        val category = categoryRepository.save(Category("gogo"))
        val board1 = Board("title1", "content1", false, "url1", category, 1L, 0, 0)
        board1.addHashTag(Arrays.asList("1", "2"), 1)
        val board2 = Board("title2", "content2", false, "url2", category, 1L, 0, 0)
        board2.addHashTag(Arrays.asList("1", "2"), 1)
        val board3 = Board("title3", "content3", false, "url3", category, 1L, 0, 0)
        board3.addHashTag(Arrays.asList("1", "2"), 1)

        boardRepository.saveAll(listOf(board1, board2, board3))
        val request = RetrieveBoardRequest(1, 3, null, "gogo", null)

        // when
        val response = boardService.retrieveBoard(request)

        // then
        val boardList = boardRepository.findAll()
        assertThat(boardList).hasSize(3)
        assertThat(response.boardList).hasSize(3)
        assertThat(response.totalPage).isEqualTo(1)
        TestUtils.assertBoardInfoResponse(response.boardList[0], board3.title, board3.content, board3.boardThumbnailUrl)
        TestUtils.assertBoardInfoResponse(response.boardList[1], board2.title, board2.content, board2.boardThumbnailUrl)
        TestUtils.assertBoardInfoResponse(response.boardList[2], board1.title, board1.content, board1.boardThumbnailUrl)
    }

    @DisplayName("title search 조건으로 게시글 불러오기")
    @Test
    fun retrieveBoard_4() {
        // given
        val category = categoryRepository.save(Category("gogo"))
        val board1 = Board("title1", "content1", false, "url1", category, 1L, 0, 0)
        board1.addHashTag(Arrays.asList("생일", "기념"), 1)
        val board2 = Board("title2", "content2", false, "url2", category, 1L, 0, 0)
        board2.addHashTag(Arrays.asList("강릉"), 1)
        val board3 = Board("title3", "content3", false, "url3", category, 1L, 0, 0)
        board3.addHashTag(Arrays.asList("생일", "강릉"), 1)

        boardRepository.saveAll(listOf(board1, board2, board3))
        val request = RetrieveBoardRequest(1, 3, "ti", null, null)

        // when
        val response = boardService.retrieveBoard(request)

        // then
        val boardList = boardRepository.findAll()
        assertThat(boardList).hasSize(3)
        assertThat(response.boardList).hasSize(3)
        assertThat(response.totalPage).isEqualTo(1)
        TestUtils.assertBoardInfoResponse(response.boardList[0], board3.title, board3.content, board3.boardThumbnailUrl)
        TestUtils.assertBoardInfoResponse(response.boardList[1], board2.title, board2.content, board2.boardThumbnailUrl)
    }

    @DisplayName("title search 조건으로 게시글 불러오기")
    @Test
    fun retrieveBoard_5() {
        // given
        val category = categoryRepository.save(Category("gogo"))
        val board1 = Board("title1", "content1", false, "url1", category, 1L, 0, 0)
        board1.addHashTag(Arrays.asList("생일", "기념"), 1)
        val board2 = Board("title2", "content2", false, "url2", category, 1L, 0, 0)
        board2.addHashTag(Arrays.asList("강릉"), 1)
        val board3 = Board("title3", "content3", false, "url3", category, 1L, 0, 0)
        board3.addHashTag(Arrays.asList("생일", "강릉"), 1)

        boardRepository.saveAll(listOf(board1, board2, board3))
        val request = RetrieveBoardRequest(1, 3, "3", null, "강릉")

        // when
        val response = boardService.retrieveBoard(request)

        // then
        val boardList = boardRepository.findAll()
        assertThat(boardList).hasSize(3)
        assertThat(response.boardList).hasSize(1)
        assertThat(response.totalPage).isEqualTo(1)
        TestUtils.assertBoardInfoResponse(response.boardList[0], board3.title, board3.content, board3.boardThumbnailUrl)
    }

    @DisplayName("특정 게시글 불러오기")
    @Test
    fun getBoard() {
        // given
        val category = categoryRepository.save(Category("gogo"))
        val board = Board("title1", "content1", false, "url1", category, 1L, 0, 0)
        board.addHashTag(listOf("1", "2") as MutableList<String>, 1)
        boardRepository.save(board)

        // when
        val response = boardService.getBoard(board.id)

        // then
        val boardList = boardRepository.findAll()
        assertThat(boardList).hasSize(1)
        TestUtils.assertBoardInfoResponse(response, board.title, board.content, board.boardThumbnailUrl)
    }

    @DisplayName("특정 게시글 불러올 때 없는 게시글이면 예외처리한다.")
    @Test
    fun getBoard_error() {
        // when & then
        assertThatThrownBy {
            boardService.getBoard(1L)
        }.isInstanceOf(NotFoundException::class.java)
    }
}