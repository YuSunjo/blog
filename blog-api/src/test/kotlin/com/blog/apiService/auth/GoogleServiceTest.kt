package com.blog.apiService.auth

import com.blog.domain.member.Member
import com.blog.domain.member.Provider
import com.blog.domain.member.repository.MemberRepository
import com.blog.dto.auth.AuthRequest
import com.blog.service.auth.factory.AuthFactoryService
import com.blog.service.member.MemberService
import com.blog.service.auth.GoogleService
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.TestConstructor

@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@SpringBootTest
class GoogleServiceTest(
    private val memberRepository: MemberRepository,
    private val memberService: MemberService,
) {

    private lateinit var authFactoryService: AuthFactoryService

    @BeforeEach
    fun setUp() {
        authFactoryService = GoogleService(memberRepository)
    }

    @AfterEach
    fun cleanUp() {
        memberRepository.deleteAll()
    }

    @DisplayName("멤버를 넘겨주면 jwt 반환를 반환한다.")
    @Test
    fun authMemberLogin() {
        // given
        val request = AuthRequest("tnswh2023@gmail.com", "tnswh", Provider.GOOGLE, "1234")
        val member = authFactoryService.findSocialIdAndProvider(request)

        // when
        val jwtToken = memberService.authMemberLogin(member)

        // then
        assertThat(jwtToken).startsWith("ey")
    }

    @DisplayName("이미 존재하는 멤버면 저장하지 않고 조회 후 멤버를 반환한다.")
    @Test
    fun authGoogleMemberLogin_2() {
        // given
        val request = AuthRequest("tnswh2023@gmail.com", "tnswh", Provider.GOOGLE, "1234")
        memberRepository.save(Member.testGoogleInstance("1234"))

        // when
        authFactoryService.findSocialIdAndProvider(request)

        // then
        val memberList = memberRepository.findAll()
        assertThat(memberList).hasSize(1)
    }

    @DisplayName("존재하지 않는 멤버면 저장하고 멤버를 반환한다..")
    @Test
    fun authGoogleMemberLogin_3() {
        // given
        val request = AuthRequest("tnswh2023@gmail.com", "tnswh", Provider.GOOGLE, "1234")

        // when
        authFactoryService.findSocialIdAndProvider(request)

        // then
        val memberList = memberRepository.findAll()
        assertThat(memberList).hasSize(1)
    }
}