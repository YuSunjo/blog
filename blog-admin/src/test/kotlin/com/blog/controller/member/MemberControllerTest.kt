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
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*
import org.springframework.restdocs.payload.PayloadDocumentation.*
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.*

@SpringBootTest
@AutoConfigureRestDocs
@AutoConfigureMockMvc
class MemberControllerTest(
    @Autowired
    private var mockMvc: MockMvc,

    @Autowired
    private val objectMapper: ObjectMapper,

    @Autowired
    private val memberRepository: MemberRepository,

    @Autowired
    private val passwordEncoder: PasswordEncoder,

    @Autowired
    private val jwtTokenProvider: JwtTokenProvider,
) {

    @AfterEach
    fun clean() {
        memberRepository.deleteAll()
    }

    @Test
    fun createMember() {
        // given
        val request = CreateMemberRequest("tnswh2023@naver.com", "1111", null, Provider.LOCAL, "nickname")

        // when & then
        mockMvc.perform(
            post("/api/v1/admin/member/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
                .accept(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andDo(document("member/create",
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
                    fieldWithPath("data").description("ok"),
                    fieldWithPath("code").description("code")
                )
            ))
    }

    @Test
    fun loginMember() {
        // given
        val encodedPassword = passwordEncoder.encode("1111")
        val member = Member("tnswh2023@naver.com", encodedPassword, null, Provider.LOCAL, Role.ADMIN, "nickname")
        memberRepository.save(member)

        val request = LoginMemberRequest("tnswh2023@naver.com", "1111", Provider.LOCAL)

        // when & then
        mockMvc.perform(
            post("/api/v1/admin/member/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
                .accept(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andDo(document("member/login",
                ApiDocumentUtils.documentRequest,
                ApiDocumentUtils.documentResponse,
                requestFields(
                    fieldWithPath("email").description("email"),
                    fieldWithPath("password").description("password"),
                    fieldWithPath("provider").description("provider")
                ),
                responseFields(
                    fieldWithPath("data").description("ok"),
                    fieldWithPath("code").description("code")
                )
            ))
    }

    @Test
    fun getMember() {
        // given
        val email = "tnswh2023@naver.com"
        val encodedPassword = passwordEncoder.encode("1111")
        val member = Member(email, encodedPassword, null, Provider.LOCAL, Role.ADMIN, "nickname")
        memberRepository.save(member)

        val token: String = jwtTokenProvider.createToken(member.id.toString())

        // when & then
        mockMvc.perform(
            get("/api/v1/admin/member")
                .header("Authorization", token)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andDo(document("member/getMember",
                ApiDocumentUtils.documentRequest,
                ApiDocumentUtils.documentResponse,
                responseFields(
                    fieldWithPath("data.id").description("id"),
                    fieldWithPath("data.email").description("email"),
                    fieldWithPath("data.memberImage").description("memberImage"),
                    fieldWithPath("data.provider").description("provider"),
                    fieldWithPath("data.role").description("role"),
                    fieldWithPath("data.nickname").description("nickname"),
                    fieldWithPath("code").description("code")
                )
            ))
    }

}