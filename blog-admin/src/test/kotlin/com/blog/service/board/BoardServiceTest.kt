package com.blog.service.board

import com.blog.domain.board.Board
import com.blog.domain.board.repository.BoardRepository
import com.blog.domain.category.Category
import com.blog.domain.category.repository.CategoryRepository
import com.blog.dto.board.CreateBoardRequest
import com.blog.dto.board.RetrieveBoardRequest
import com.blog.dto.board.UpdateBoardRequest
import com.blog.service.TestUtils
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.util.*

@SpringBootTest
class BoardServiceTest(
    @Autowired
    private val boardService: BoardService,

    @Autowired
    private val boardRepository: BoardRepository,

    @Autowired
    private val categoryRepository: CategoryRepository,
) {
    @AfterEach
    fun cleanUp() {
        boardRepository.deleteAll()
        categoryRepository.deleteAll()
    }

    @Test
    fun createBoard() {
        // given
        val category = categoryRepository.save(Category("gogo"))

        val hashTagList = mutableListOf("자바", "코틀린", "살자")
        val request = CreateBoardRequest("title", "content", false, "url", category.id, hashTagList)

        // when
        boardService.createBoard(request, 1L)

        // then
        val boardList = boardRepository.findAll()
        assertThat(boardList).hasSize(1)
        TestUtils.assertBoard(boardList[0], request.title, request.content, request.boardThumbnailUrl)
    }

    @Test
    fun updateBoard() {
        // given
        val hashTagList = mutableListOf("자바", "코틀린", "살자")
        val category = categoryRepository.save(Category("gogo"))
        val board = Board("title", "content", false, "url", category, 1L, 0, 0)
        boardRepository.save(board)
        val request =
            UpdateBoardRequest(board.id, "updateTitle", "updateContent", false, "updateUrl", category.id, hashTagList)

        // when
        boardService.updateBoard(request, 1L)

        // then
        val boardList = boardRepository.findAll()
        assertThat(boardList).hasSize(1)
        TestUtils.assertBoard(boardList[0], request.title, request.content, request.boardThumbnailUrl)
    }

    @Test
    fun getBoard() {
        // given
        val category = categoryRepository.save(Category("gogo"))
        val board = Board("title", "content", false, "url", category, 1L, 0, 0)
        boardRepository.save(board)

        // when
        val response = boardService.getBoard(board.id)

        // then
        val boardList = boardRepository.findAll()
        assertThat(boardList).hasSize(1)
        TestUtils.assertBoardInfoResponse(response, board.title, board.content, board.boardThumbnailUrl)
    }

    @DisplayName("특정 조건 없이 게시글 불러오기")
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
        assertThat(response.totalPage).isEqualTo(2)
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
        val request = RetrieveBoardRequest(1, 3, null, "gogo", listOf("1"))

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

    @DisplayName("hashTagList를 통해서 게시글 불러오기")
    @Test
    fun retrieveBoard_3() {
        // given
        val category = categoryRepository.save(Category("gogo"))
        val board1 = Board("title1", "content1", false, "url1", category, 1L, 0, 0)
        board1.addHashTag(Arrays.asList("생일", "기념"), 1)
        val board2 = Board("title2", "content2", false, "url2", category, 1L, 0, 0)
        board2.addHashTag(Arrays.asList("강릉"), 1)
        val board3 = Board("title3", "content3", false, "url3", category, 1L, 0, 0)
        board3.addHashTag(Arrays.asList("생일", "강릉"), 1)

        boardRepository.saveAll(listOf(board1, board2, board3))
        val request = RetrieveBoardRequest(1, 3, null, null, listOf("강릉"))

        // when
        val response = boardService.retrieveBoard(request)

        // then
        val boardList = boardRepository.findAll()
        assertThat(boardList).hasSize(3)
        assertThat(response.boardList).hasSize(2)
        assertThat(response.totalPage).isEqualTo(1)
        TestUtils.assertBoardInfoResponse(response.boardList[0], board3.title, board3.content, board3.boardThumbnailUrl)
        TestUtils.assertBoardInfoResponse(response.boardList[1], board2.title, board2.content, board2.boardThumbnailUrl)
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
        val request = RetrieveBoardRequest(1, 3, "ti", null, listOf("강릉"))

        // when
        val response = boardService.retrieveBoard(request)

        // then
        val boardList = boardRepository.findAll()
        assertThat(boardList).hasSize(3)
        assertThat(response.boardList).hasSize(2)
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
        val request = RetrieveBoardRequest(1, 3, "3", null, listOf("강릉"))

        // when
        val response = boardService.retrieveBoard(request)

        // then
        val boardList = boardRepository.findAll()
        assertThat(boardList).hasSize(3)
        assertThat(response.boardList).hasSize(1)
        assertThat(response.totalPage).isEqualTo(1)
        TestUtils.assertBoardInfoResponse(response.boardList[0], board3.title, board3.content, board3.boardThumbnailUrl)
    }

}