package com.blog.apiService.member

import com.blog.TestUtils
import com.blog.domain.member.Member
import com.blog.domain.member.Provider
import com.blog.domain.member.Role
import com.blog.domain.member.repository.MemberRepository
import com.blog.dto.member.CreateMemberRequest
import com.blog.dto.member.LoginMemberRequest
import com.blog.service.member.MemberService
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.test.context.TestConstructor

@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@SpringBootTest
class MemberServiceTest(
    private val memberService: MemberService,
    private val passwordEncoder: PasswordEncoder,
    private val memberRepository: MemberRepository
) {

    @AfterEach
    fun cleanUp() {
        memberRepository.deleteAll()
    }

    @DisplayName("새로운 멤버 가입")
    @Test
    fun memberSignUp() {
        // given
        val request = CreateMemberRequest("tnswh2023@naver.com", "123", null, Provider.LOCAL, "hello")

        // when
        memberService.memberSignUp(request)

        // then
        val memberList = memberRepository.findAll()
        assertThat(memberList).hasSize(1)
        TestUtils.assertMember(memberList[0], request.email, request.nickname, request.provider)
    }

    @DisplayName("멤버 로그인하면 jwt토큰을 리턴한다.")
    @Test
    fun memberLogin() {
        // given
        val email = "tnswh2023@naver.com"
        val nickname = "nickname"
        val member = Member(email, passwordEncoder.encode("11"), null, Provider.LOCAL, Role.USER, nickname, null)
        memberRepository.save(member)

        val request = LoginMemberRequest(email, "11")

        // when
        val jwtToken = memberService.memberLogin(request)

        // then
        val memberList = memberRepository.findAll()
        assertThat(memberList).hasSize(1)
        assertThat(jwtToken).startsWith("ey")
    }

    @DisplayName("멤버를 조회한다.")
    @Test
    fun getMember() {
        // given
        val email = "tnswh2023@naver.com"
        val nickname = "nickname"
        val member = Member(email, passwordEncoder.encode("11"), null, Provider.LOCAL, Role.USER, nickname, null)
        memberRepository.save(member)

        // when
        val memberInfoResponse = memberService.getMember(member.id)

        // then
        val memberList = memberRepository.findAll()
        assertThat(memberList).hasSize(1)
        TestUtils.assertMember(member, memberInfoResponse?.email, memberInfoResponse?.nickname, memberInfoResponse?.provider)
    }

}