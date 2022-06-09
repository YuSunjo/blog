package com.blog.service.auth

import com.blog.domain.member.Member
import com.blog.domain.member.Provider
import com.blog.domain.member.repository.MemberRepository
import com.blog.dto.auth.AuthRequest
import com.blog.service.auth.factory.AuthFactoryService
import org.springframework.stereotype.Service

@Service
class GoogleService(
    private val memberRepository: MemberRepository
) : AuthFactoryService {

    override fun findSocialIdAndProvider(request: AuthRequest): Member {
        return memberRepository.findBySocialIdAndProvider(request.accessToken, request.provider)
            ?: memberRepository.save(Member.newMember(request.accessToken, Provider.GOOGLE, request.email, request.name))
    }

}