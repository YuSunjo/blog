package com.blog.apiService.auth

import com.blog.domain.member.Member
import com.blog.domain.member.Provider
import com.blog.domain.member.repository.MemberRepository
import com.blog.dto.auth.AuthRequest
import com.blog.dto.auth.FacebookMemberInfoResponse
import com.blog.service.auth.factory.AuthFactoryService
import com.blog.service.auth.FacebookApiCallerFeignClient
import com.blog.service.auth.FacebookService
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.TestConstructor

@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@SpringBootTest
class FacebookServiceTest(
    private val memberRepository: MemberRepository,
) {

    private lateinit var authFactoryService: AuthFactoryService

    @BeforeEach
    fun setUp() {
        authFactoryService = FacebookService(memberRepository, FacebookApiCallerFeignClientStub())
    }

    @DisplayName("이미 존재하는 멤버면 저장하지 않고 조회하고 멤버를 반환환다.")
    @Test
    fun authFacebookMemberLogin_2() {
        // given
        val request = AuthRequest(null, null, Provider.FACEBOOK, "accessToken")
        memberRepository.save(Member.testFacebookInstance("1234"))

        // when
        authFactoryService.findSocialIdAndProvider(request)

        // then
        val memberList = memberRepository.findAll()
        Assertions.assertThat(memberList).hasSize(1)
    }

    @DisplayName("존재하지 않는 멤버면 저장하고 멤버를 반환한다.")
    @Test
    fun authFacebookMemberLogin_3() {
        // given
        val request = AuthRequest(null, null, Provider.GOOGLE, "accessToken")

        // when
        authFactoryService.findSocialIdAndProvider(request)

        // then
        val memberList = memberRepository.findAll()
        Assertions.assertThat(memberList).hasSize(1)
    }

    inner class FacebookApiCallerFeignClientStub : FacebookApiCallerFeignClient {

        override fun getFacebookMemberInfo(access_token: String, fields: List<String>): FacebookMemberInfoResponse {
            return FacebookMemberInfoResponse("1234", "tnswh")
        }

    }

}