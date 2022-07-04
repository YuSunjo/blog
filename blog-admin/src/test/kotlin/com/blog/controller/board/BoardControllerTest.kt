package com.blog.controller.board

import com.blog.ApiDocumentUtils
import com.blog.config.jwt.JwtTokenProvider
import com.blog.domain.board.Board
import com.blog.domain.board.BoardHashTag
import com.blog.domain.board.repository.BoardHashTagRepository
import com.blog.domain.board.repository.BoardRepository
import com.blog.domain.category.Category
import com.blog.domain.category.repository.CategoryRepository
import com.blog.domain.member.Member
import com.blog.domain.member.Provider
import com.blog.domain.member.Role
import com.blog.domain.member.repository.MemberRepository
import com.blog.dto.board.CreateBoardRequest
import com.blog.dto.board.UpdateBoardRequest
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*
import org.springframework.restdocs.payload.PayloadDocumentation.*
import org.springframework.restdocs.request.RequestDocumentation.parameterWithName
import org.springframework.restdocs.request.RequestDocumentation.pathParameters
import org.springframework.test.context.TestConstructor
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print

@SpringBootTest
@AutoConfigureRestDocs
@AutoConfigureMockMvc
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
class BoardControllerTest(
    private var mockMvc: MockMvc,
    private val objectMapper: ObjectMapper,
    private val jwtTokenProvider: JwtTokenProvider,
    private val boardRepository: BoardRepository,
    private val categoryRepository: CategoryRepository,
    private val memberRepository: MemberRepository,
    private val boardHashTagRepository: BoardHashTagRepository,
) {
    @AfterEach
    fun clean() {
        boardRepository.deleteAll()
        categoryRepository.deleteAll()
        memberRepository.deleteAll()
        boardHashTagRepository.deleteAll()
    }

    var token: String = ""
    val category = Category("kotlin")
    val member = Member("tnswh2023@naver.com", "1111", null, Provider.LOCAL, Role.ADMIN, "nickname", null)
    @BeforeEach
    fun setUp() {
        categoryRepository.save(category)

        memberRepository.save(member)

        token = jwtTokenProvider.createToken(member.id.toString())
    }

    @Test
    fun createBoard() {
        // given
        val request = CreateBoardRequest("title", "content", false, null, category.id,
            listOf("2022", "2021") as MutableList<String>
        )

        // when & then
        mockMvc.perform(
            post("/api/v1/board")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", token)
                .content(objectMapper.writeValueAsString(request))
                .accept(MediaType.APPLICATION_JSON)
        )
            .andDo(print())
            .andDo(
                document(
                    "board/create",
                    ApiDocumentUtils.documentRequest,
                    ApiDocumentUtils.documentResponse,
                    requestFields(
                        fieldWithPath("title").description("title"),
                        fieldWithPath("content").description("content"),
                        fieldWithPath("isPrivate").description("isPrivate"),
                        fieldWithPath("boardThumbnailUrl").description("boardThumbnailUrl"),
                        fieldWithPath("categoryId").description("categoryId"),
                        fieldWithPath("hashTagList").description("hashTagList")
                    ),
                    responseFields(
                        fieldWithPath("data.id").description("id"),
                        fieldWithPath("data.title").description("email"),
                        fieldWithPath("data.content").description("content"),
                        fieldWithPath("data.isPrivate").description("isPrivate"),
                        fieldWithPath("data.boardThumbnailUrl").description("boardThumbnailUrl"),
                        fieldWithPath("data.categoryName").description("categoryName"),
                        fieldWithPath("data.categoryId").description("categoryId"),
                        fieldWithPath("data.memberId").description("memberId"),
                        fieldWithPath("data.createdDate").description("createdDate"),
                        fieldWithPath("data.hashTagList[].hashTag").description("hashTagList"),

                        fieldWithPath("code").description("code"),
                    )
                )
            )
    }

    @Test
    fun updateBoard() {
        // given
        val board = Board("title", "content", false, null, category, member.id)
        boardRepository.save(board)

        val request = UpdateBoardRequest(board.id, "title", "content", false, null, category.id,
            listOf("2022", "2021") as MutableList<String>
        )

        // when & then
        mockMvc.perform(
            put("/api/v1/board")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", token)
                .content(objectMapper.writeValueAsString(request))
                .accept(MediaType.APPLICATION_JSON)
        )
            .andDo(print())
            .andDo(
                document(
                    "board/update",
                    ApiDocumentUtils.documentRequest,
                    ApiDocumentUtils.documentResponse,
                    requestFields(
                        fieldWithPath("id").description("id"),
                        fieldWithPath("title").description("title"),
                        fieldWithPath("content").description("content"),
                        fieldWithPath("isPrivate").description("isPrivate"),
                        fieldWithPath("boardThumbnailUrl").description("boardThumbnailUrl"),
                        fieldWithPath("categoryId").description("categoryId"),
                        fieldWithPath("hashTagList").description("hashTagList")
                    ),
                    responseFields(
                        fieldWithPath("data.id").description("id"),
                        fieldWithPath("data.title").description("email"),
                        fieldWithPath("data.content").description("content"),
                        fieldWithPath("data.isPrivate").description("isPrivate"),
                        fieldWithPath("data.boardThumbnailUrl").description("boardThumbnailUrl"),
                        fieldWithPath("data.categoryName").description("categoryName"),
                        fieldWithPath("data.categoryId").description("categoryId"),
                        fieldWithPath("data.memberId").description("memberId"),
                        fieldWithPath("data.createdDate").description("createdDate"),
                        fieldWithPath("data.hashTagList[].hashTag").description("hashTagList"),

                        fieldWithPath("code").description("code"),
                    )
                )
            )
    }

    @Test
    fun getBoard() {
        // given
        val board = Board("title", "content", false, null, category, member.id)
        boardRepository.save(board)

        // when & then
        mockMvc.perform(
            get("/api/v1/board/{boardId}", board.id)
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", token)
                .accept(MediaType.APPLICATION_JSON)
        )
            .andDo(print())
            .andDo(
                document(
                    "board/getBoard",
                    ApiDocumentUtils.documentRequest,
                    ApiDocumentUtils.documentResponse,
                    pathParameters(
                        parameterWithName("boardId").description("게시글 아이디")
                    ),
                    responseFields(
                        fieldWithPath("data.id").description("id"),
                        fieldWithPath("data.title").description("email"),
                        fieldWithPath("data.content").description("content"),
                        fieldWithPath("data.isPrivate").description("isPrivate"),
                        fieldWithPath("data.boardThumbnailUrl").description("boardThumbnailUrl"),
                        fieldWithPath("data.categoryName").description("categoryName"),
                        fieldWithPath("data.categoryId").description("categoryId"),
                        fieldWithPath("data.memberId").description("memberId"),
                        fieldWithPath("data.createdDate").description("createdDate"),
                        fieldWithPath("data.hashTagList[]").description("hashTagList"),

                        fieldWithPath("code").description("code"),
                    )
                )
            )
    }

    @Test
    fun boardList() {
        // given
        val board1 = Board("title1", "content", false, null, category, member.id)
        val board2 = Board("title2", "content", false, null, category, member.id)
        boardRepository.saveAll(listOf(board1, board2))

        // when & then
        mockMvc.perform(
            get("/api/v1/board/list?page=1&size=5")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", token)
                .accept(MediaType.APPLICATION_JSON)
        )
            .andDo(print())
            .andDo(
                document(
                    "board/list",
                    ApiDocumentUtils.documentRequest,
                    ApiDocumentUtils.documentResponse,
                    responseFields(
                        fieldWithPath("data.boardList[].id").description("id"),
                        fieldWithPath("data.boardList[].title").description("email"),
                        fieldWithPath("data.boardList[].content").description("content"),
                        fieldWithPath("data.boardList[].isPrivate").description("isPrivate"),
                        fieldWithPath("data.boardList[].boardThumbnailUrl").description("boardThumbnailUrl"),
                        fieldWithPath("data.boardList[].categoryName").description("categoryName"),
                        fieldWithPath("data.boardList[].categoryId").description("categoryId"),
                        fieldWithPath("data.boardList[].memberId").description("memberId"),
                        fieldWithPath("data.boardList[].createdDate").description("createdDate"),
                        fieldWithPath("data.boardList[].hashTagList[]").description("hashTagList"),
                        fieldWithPath("data.totalPage").description("totalPage"),

                        fieldWithPath("code").description("code"),
                    )
                )
            )
    }

    @Test
    fun hashTagList() {
        // given
        val board1 = Board("title1", "content", false, null, category, member.id)
        val board2 = Board("title2", "content", false, null, category, member.id)
        boardRepository.saveAll(listOf(board1, board2))
        val boardHashTag = BoardHashTag(member.id, "2022", board1)
        val boardHashTag2 = BoardHashTag(member.id, "2021", board2)
        boardHashTagRepository.saveAll(listOf(boardHashTag, boardHashTag2))

        // when & then
        mockMvc.perform(
            get("/api/v1/hashTag")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", token)
                .accept(MediaType.APPLICATION_JSON)
        )
            .andDo(print())
            .andDo(
                document(
                    "board/hashTagList",
                    ApiDocumentUtils.documentRequest,
                    ApiDocumentUtils.documentResponse,
                    responseFields(
                        fieldWithPath("data.[]").description("hashTag"),

                        fieldWithPath("code").description("code"),
                    )
                )
            )
    }

}