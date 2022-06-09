package com.blog.service.board

import com.blog.domain.board.Board
import com.blog.domain.board.repository.BoardHashTagRepository
import com.blog.domain.board.repository.BoardRepository
import com.blog.domain.category.Category
import com.blog.domain.category.repository.CategoryRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.TestConstructor

@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@SpringBootTest
class BoardServiceTest(
    private val boardRepository: BoardRepository,
    private val boardHashTagRepository: BoardHashTagRepository,
    private val categoryRepository: CategoryRepository,
    private val boardService: BoardService
) {

    @AfterEach
    fun clean() {
        boardRepository.deleteAll()
        boardHashTagRepository.deleteAll()
        categoryRepository.deleteAll()
    }

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

}