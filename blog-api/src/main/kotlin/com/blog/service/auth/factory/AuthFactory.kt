package com.blog.service.auth.factory

import com.blog.domain.member.Provider
import com.blog.exception.NotFoundException
import com.blog.service.auth.FacebookService
import com.blog.service.auth.GoogleService
import org.springframework.stereotype.Component
import java.util.*
import javax.annotation.PostConstruct

private val authServiceMap: MutableMap<Provider, AuthFactoryService> = EnumMap(Provider::class.java)

@Component
class AuthFactory (
    private val googleService: GoogleService,
    private val facebookService: FacebookService,
) {

    @PostConstruct
    fun socialServiceMap() {
        authServiceMap[Provider.FACEBOOK] = facebookService
        authServiceMap[Provider.GOOGLE] = googleService
    }

    fun getAuthService(provider: Provider): AuthFactoryService {
        return authServiceMap[provider] ?: throw NotFoundException("존재하지 않는 authService ($provider) 입니다.")
    }

}