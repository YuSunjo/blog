package com.blog.service.auth

import com.blog.component.GoogleAuthComponent
import com.blog.dto.auth.GoogleAccessTokenResponse
import com.blog.dto.auth.GoogleAuthRequest
import com.blog.dto.auth.GoogleMemberInfoResponse
import com.blog.exception.ValidationException
import org.springframework.stereotype.Component

@Component
class GoogleClient(
    private val googleApiCallerFeignClient: AuthApiCallerFeignClient,
    private val googleAuthComponent: GoogleAuthComponent
) {

    fun googleAuth(code: String, redirectUri: String): GoogleMemberInfoResponse {
        val request: GoogleAuthRequest = this.createRequest(code, redirectUri)
        val googleAccessTokenResponse: GoogleAccessTokenResponse = googleApiCallerFeignClient.tokenAuthentication(request)
        return googleApiCallerFeignClient.getGoogleMemberInfo(createBearer(googleAccessTokenResponse.accessToken))
    }

    private fun createRequest(code: String, redirectUri: String): GoogleAuthRequest {
        println("grantType" + googleAuthComponent.grantType)
        return GoogleAuthRequest(googleAuthComponent.grantType, code, googleAuthComponent.clientId, googleAuthComponent.clientSecret, redirectUri)
    }

    private fun createBearer(accessToken: String?): String {
        accessToken ?: throw ValidationException("accessToken 이 null입니다...")
        return String.format("Bearer %s", accessToken)
    }

}