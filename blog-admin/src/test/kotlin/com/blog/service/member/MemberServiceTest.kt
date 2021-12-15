package com.blog.service.member

import com.blog.domain.admin.Admin
import com.blog.domain.admin.repository.AdminRepository
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
    private val adminRepository: AdminRepository,
    @Autowired
    private val adminMemberService: MemberService,
    @Autowired
    private val passwordEncoder: PasswordEncoder
) {

    @AfterEach
    fun cleanUp() {
        adminRepository.deleteAll()
    }

    @Test
    fun createMember() {
        // given
        val request = CreateMemberRequest("tnswh2023@naver.com", "1234")
        adminMemberService.createMember(request)

        // when
        val memberList = adminRepository.findAll()

        // then
        assertThat(memberList).hasSize(1)
        assertThat(memberList[0].email).isEqualTo(request.email)
    }

    @Test
    fun loginMember() {
        // given
        val admin = Admin("tnswh2023@naver.com", passwordEncoder.encode("1234"), null)
        adminRepository.save(admin)
        val request = LoginMemberRequest("tnswh2023@naver.com", "1234")

        // when
        val response = adminMemberService.adminMemberLogin(request)

        // then
        assertThat(response).startsWith("ey")
    }

    @Test
    fun getMyInfo() {
        // given
        val admin = Admin("tnswh2023@naver.com", passwordEncoder.encode("1234"), null)
        adminRepository.save(admin)

        // when
        val response = adminMemberService.getMyInfo(admin.id)

        // then
        val adminList = adminRepository.findAll()
        assertThat(adminList).hasSize(1)
        assertThat(response.id).isEqualTo(adminList[0].id)
        assertThat(response.email).isEqualTo(adminList[0].email)
    }

}