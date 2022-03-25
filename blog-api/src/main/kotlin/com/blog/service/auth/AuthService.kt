package com.blog.service.auth

import com.blog.config.jwt.JwtTokenProvider
import com.blog.domain.member.Member
import com.blog.domain.member.Provider
import com.blog.domain.member.Role
import com.blog.domain.member.repository.MemberRepository
import com.blog.dto.auth.AuthRequest
import com.blog.dto.auth.GoogleMemberInfoResponse
import org.springframework.stereotype.Service

@Service
class AuthService(
    private val googleClient: GoogleClient,
    private val jwtTokenProvider: JwtTokenProvider,
    private val memberRepository: MemberRepository
) {
    fun googleAuthentication(request: AuthRequest): String {
        val googleMemberInfoResponse: GoogleMemberInfoResponse = googleClient.googleAuth(request.code, request.redirectUri)
        val member: Member = memberRepository.findMemberByEmailAndRole(googleMemberInfoResponse.email, Role.USER)
            ?: memberRepository.save(Member.newMember(googleMemberInfoResponse, Provider.GOOGLE))
        return jwtTokenProvider.createToken(member.id.toString())
    }

}