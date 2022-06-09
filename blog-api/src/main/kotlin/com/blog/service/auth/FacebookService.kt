package com.blog.service.auth

import com.blog.domain.member.Member
import com.blog.domain.member.Provider
import com.blog.domain.member.repository.MemberRepository
import com.blog.dto.auth.AuthRequest
import com.blog.service.auth.factory.AuthFactoryService
import org.springframework.stereotype.Service

@Service
class FacebookService(
    private val memberRepository: MemberRepository,
    private val facebookApiCallerFeignClient: FacebookApiCallerFeignClient
) : AuthFactoryService {

    override fun findSocialIdAndProvider(request: AuthRequest): Member {
        val facebookMemberInfoResponse = facebookApiCallerFeignClient.getFacebookMemberInfo(request.accessToken, listOf("id", "name"))
        return memberRepository.findBySocialIdAndProvider(facebookMemberInfoResponse.id , Provider.FACEBOOK)
            ?: memberRepository.save(Member.newMember(facebookMemberInfoResponse.id, Provider.FACEBOOK, null, facebookMemberInfoResponse.name))
    }

}