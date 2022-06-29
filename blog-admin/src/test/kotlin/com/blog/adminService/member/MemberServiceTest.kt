package com.blog.adminService.member

import com.blog.service.member.MemberService
import com.blog.domain.member.Member
import com.blog.domain.member.Provider
import com.blog.domain.member.Role
import com.blog.domain.member.repository.MemberRepository
import com.blog.dto.adminMember.CreateMemberRequest
import com.blog.dto.adminMember.LoginMemberRequest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.crypto.password.PasswordEncoder

@SpringBootTest
internal class MemberServiceTest(
    @Autowired
    private val memberRepository: MemberRepository,
    @Autowired
    private val adminMemberService: MemberService,
    @Autowired
    private val passwordEncoder: PasswordEncoder
) {

    @AfterEach
    fun cleanUp() {
        memberRepository.deleteAll()
    }

    @Test
    fun createMember() {
        // given
        val request = CreateMemberRequest("tnswh2023@naver.com", "1234")
        adminMemberService.createMember(request)

        // when
        val memberList = memberRepository.findAll()

        // then
        assertThat(memberList).hasSize(1)
        assertThat(memberList[0].email).isEqualTo(request.email)
        assertThat(memberList[0].role).isEqualTo(Role.ADMIN)
    }

    @Test
    fun loginMember() {
        // given
        val admin = Member("tnswh2023@naver.com", passwordEncoder.encode("1234"), null, Provider.LOCAL, Role.ADMIN, "ADMIN", null)
        memberRepository.save(admin)
        val request = LoginMemberRequest("tnswh2023@naver.com", "1234")

        // when
        val response = adminMemberService.adminMemberLogin(request)

        // then
        assertThat(response).startsWith("ey")
    }

    @Test
    fun getMyInfo() {
        // given
        val admin = Member("tnswh2023@naver.com", passwordEncoder.encode("1234"), null, Provider.LOCAL, Role.ADMIN, "ADMIN", null)
        memberRepository.save(admin)

        // when
        val response = adminMemberService.getMyInfo(admin.id)

        // then
        val adminList = memberRepository.findAll()
        assertThat(adminList).hasSize(1)
        assertThat(response?.id).isEqualTo(adminList[0].id)
        assertThat(response?.email).isEqualTo(adminList[0].email)
    }
}