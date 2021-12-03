package com.blog.service.member

import com.blog.config.jwt.JwtTokenProvider
import com.blog.domain.admin.Admin
import com.blog.domain.admin.repository.AdminRepository
import com.blog.dto.member.CreateMemberRequest
import com.blog.dto.member.LoginMemberRequest
import com.blog.exception.NotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class MemberService(
    private val adminRepository: AdminRepository,
    private val passwordEncoder: PasswordEncoder,
    private val jwtTokenProvider: JwtTokenProvider
) {
    @Transactional
    fun createMember(request: CreateMemberRequest) {
        val encodedPassword = passwordEncoder.encode(request.password)
        adminRepository.save(request.toEntity(encodedPassword))
    }

    @Transactional
    fun adminMemberLogin(request: LoginMemberRequest): String {
        val admin: Admin = (adminRepository.findByEmail(request.email)
            ?: throw NotFoundException("존재하지 않는 멤버 ${request.email} 입니다."))
        MemberServiceUtils.validatePassword(passwordEncoder, admin.password, request.password)
        return jwtTokenProvider.createToken(admin.id.toString())
    }

}