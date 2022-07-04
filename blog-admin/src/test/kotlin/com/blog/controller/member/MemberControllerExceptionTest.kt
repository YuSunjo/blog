package com.blog.controller.member

import com.blog.ApiDocumentUtils
import com.blog.config.jwt.JwtTokenProvider
import com.blog.domain.member.Member
import com.blog.domain.member.Provider
import com.blog.domain.member.Role
import com.blog.domain.member.repository.MemberRepository
import com.blog.dto.member.CreateMemberRequest
import com.blog.dto.member.LoginMemberRequest
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders
import org.springframework.restdocs.payload.PayloadDocumentation.*
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.test.context.TestConstructor
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.result.MockMvcResultHandlers

@SpringBootTest
@AutoConfigureRestDocs
@AutoConfigureMockMvc
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
class MemberControllerExceptionTest(
    private var mockMvc: MockMvc,
    private val objectMapper: ObjectMapper,
    private val memberRepository: MemberRepository,
    private val passwordEncoder: PasswordEncoder,
    private val jwtTokenProvider: JwtTokenProvider,
) {

    @AfterEach
    fun clean() {
        memberRepository.deleteAll()
    }

    @DisplayName("요청 보낼때 email 이 빈문자열 이라면 exception 발생")
    @Test
    fun createMemberException_1() {
        // given
        val request = CreateMemberRequest("", "1111", null, Provider.LOCAL, "nickname")

        // when & then
        mockMvc.perform(
            RestDocumentationRequestBuilders.post("/api/v1/admin/member/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
                .accept(MediaType.APPLICATION_JSON)
        )
            .andDo(MockMvcResultHandlers.print())
            .andDo(
                MockMvcRestDocumentation.document(
                    "member/exception/create",
                    ApiDocumentUtils.documentRequest,
                    ApiDocumentUtils.documentResponse,
                    requestFields(
                        fieldWithPath("email").description("email"),
                        fieldWithPath("password").description("password"),
                        fieldWithPath("nickname").description("nickname"),
                        fieldWithPath("memberImage").description("memberImage"),
                        fieldWithPath("provider").description("provider")
                    ),
                    responseFields(
                        fieldWithPath("message").description("잘못된 입력입니다."),
                        fieldWithPath("code").description(400),
                        fieldWithPath("description").description("must not be blank"),
                    )
                )
            )
    }

    @DisplayName("로그인 할 때 비밀번호가 다르면 예외 발생")
    @Test
    fun loginMemberException_1() {
        // given
        val encodedPassword = passwordEncoder.encode("1111")
        val member = Member("tnswh2023@naver.com", encodedPassword, null, Provider.LOCAL, Role.ADMIN, "nickname", null)
        memberRepository.save(member)

        val request = LoginMemberRequest("tnswh2023@naver.com", "1234", Provider.LOCAL)

        // when & then
        mockMvc.perform(
            RestDocumentationRequestBuilders.post("/api/v1/admin/member/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
                .accept(MediaType.APPLICATION_JSON)
        )
            .andDo(MockMvcResultHandlers.print())
            .andDo(
                MockMvcRestDocumentation.document(
                    "member/exception/login",
                    ApiDocumentUtils.documentRequest,
                    ApiDocumentUtils.documentResponse,
                    requestFields(
                        fieldWithPath("email").description("email"),
                        fieldWithPath("password").description("password"),
                        fieldWithPath("provider").description("provider")
                    ),
                    responseFields(
                        fieldWithPath("message").description("message"),
                        fieldWithPath("code").description(404),
                        fieldWithPath("description").description("description"),
                    )
                )
            )
    }

    @DisplayName("존재하지 않는 유저일 경우 예외 발생")
    @Test
    fun getMember() {
        // given
        val token: String = jwtTokenProvider.createToken("1")

        // when & then
        mockMvc.perform(
            RestDocumentationRequestBuilders.get("/api/v1/admin/member")
                .header("Authorization", token)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        )
            .andDo(MockMvcResultHandlers.print())
            .andDo(
                MockMvcRestDocumentation.document(
                    "member/exception/getMember",
                    ApiDocumentUtils.documentRequest,
                    ApiDocumentUtils.documentResponse,
                    responseFields(
                        fieldWithPath("message").description("message"),
                        fieldWithPath("code").description(404),
                        fieldWithPath("description").description("description"),
                    )
                )
            )
    }

}