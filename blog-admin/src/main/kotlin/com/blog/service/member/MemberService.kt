package com.blog.service.member

import com.blog.config.jwt.JwtTokenProvider
import com.blog.domain.member.Member
import com.blog.domain.member.Role
import com.blog.domain.member.repository.MemberRepository
import com.blog.dto.adminMember.CreateMemberRequest
import com.blog.dto.adminMember.LoginMemberRequest
import com.blog.dto.member.MemberInfoResponse
import com.blog.exception.NotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class MemberService(
    private val passwordEncoder: PasswordEncoder,
    private val jwtTokenProvider: JwtTokenProvider,
    private val memberRepository: MemberRepository
) {
    @Transactional
    fun createMember(request: CreateMemberRequest) {
        MemberServiceUtils.validateEmailAndRole(memberRepository, request.email, Role.ADMIN)
        val encodedPassword = passwordEncoder.encode(request.password)
        memberRepository.save(request.toEntity(encodedPassword))
    }

    @Transactional
    fun adminMemberLogin(request: LoginMemberRequest): String {
        val member = (memberRepository.findMemberByEmailAndRole(request.email, Role.ADMIN)
            ?: throw NotFoundException("존재하지 않는 멤버 ${request.email} 입니다."))
        MemberServiceUtils.validatePassword(passwordEncoder, member.password, request.password)
        return jwtTokenProvider.createToken(member.id.toString())
    }

    @Transactional
    fun getMyInfo(memberId: Long): MemberInfoResponse {
        val member: Member = memberRepository.findMemberByIdAndRole(memberId, Role.ADMIN)
                ?: throw NotFoundException("존재하지 않는 멤버 $memberId 입니다.")
        return MemberInfoResponse.of(member)
    }

}