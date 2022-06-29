package com.blog.service.member

import com.blog.config.jwt.JwtTokenProvider
import com.blog.domain.member.Member
import com.blog.domain.member.Provider
import com.blog.domain.member.Role
import com.blog.domain.member.repository.MemberRepository
import com.blog.dto.member.CreateMemberRequest
import com.blog.dto.member.LoginMemberRequest
import com.blog.dto.member.MemberInfoResponse
import com.blog.exception.NotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class MemberService(
    private val memberRepository: MemberRepository,
    private val passwordEncoder: PasswordEncoder,
    private val jwtTokenProvider: JwtTokenProvider
) {
    @Transactional
    fun memberSignUp(request: CreateMemberRequest): MemberInfoResponse? {
        MemberServiceUtils.validateEmailAndRole(memberRepository, request.email, Role.USER)
        MemberServiceUtils.validateNickname(memberRepository, request.nickname)
        val encodedPassword = passwordEncoder.encode(request.password)
        val member: Member = memberRepository.save(request.toEntity(encodedPassword))
        return MemberInfoResponse.of(member)
    }

    @Transactional
    fun memberLogin(request: LoginMemberRequest): String {
        val member = (
            memberRepository.findMemberByEmailAndRole(request.email, Role.USER, Provider.LOCAL)
                ?: throw NotFoundException("존재하지 않는 멤버 ${request.email}입니다.")
            )
        MemberServiceUtils.validatePassword(passwordEncoder, member.password, request.password)
        return jwtTokenProvider.createToken(member.id.toString())
    }

    @Transactional
    fun getMember(memberId: Long): MemberInfoResponse? {
        val member = (
            memberRepository.findMemberById(memberId)
                ?: throw NotFoundException("존재하지 않는 멤버 $memberId 입니다.")
            )
        return MemberInfoResponse.of(member)
    }

    fun authMemberLogin(member: Member): String {
        return jwtTokenProvider.createToken(member.id.toString())
    }
}