package com.blog.service

import com.blog.config.jwt.JwtTokenProvider
import com.blog.domain.member.Member
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
    fun memberSignUp(request: CreateMemberRequest): MemberInfoResponse {
        val encodedPassword = passwordEncoder.encode(request.password)
        val member: Member = memberRepository.save(request.toEntity(encodedPassword))
        return MemberInfoResponse.of(member)
    }

    @Transactional
    fun memberLogin(request: LoginMemberRequest): String {
        val member = (memberRepository.findMemberByEmail(request.email)
            ?: throw NotFoundException("존재하지 않는 멤버 ${request.email}입니다."))
        return jwtTokenProvider.createToken(member.id.toString())
    }

}