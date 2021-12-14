package com.blog.service.auth

import com.blog.component.KakaoAuthComponent
import com.blog.dto.auth.AuthRequest
import com.blog.dto.auth.KaKaoUserInfoResponse
import com.blog.dto.auth.KakaoAccessTokenResponse
import com.blog.exception.ValidationException
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.ClientResponse
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.util.UriBuilder
import reactor.core.publisher.Mono
import java.util.function.Consumer

@Component
class AuthApiCallerImpl(
    private val kakaoAuthComponent: KakaoAuthComponent,
    private val webClient: WebClient
) : AuthApiCaller {

    override fun tokenAuthentication(request: AuthRequest): KakaoAccessTokenResponse? {
        return webClient.get()
            .uri {uriBuilder: UriBuilder ->
                uriBuilder.path("")
                    .queryParam("code", request.code)
                    .queryParam("redirect_uri", request.redirectUri)
                    .queryParam("grant_type", kakaoAuthComponent.grant_type)
                    .queryParam("client_id", kakaoAuthComponent.clientKey)
                    .build()
            }
            .retrieve()
            .onStatus(
                { obj: HttpStatus -> obj.is4xxClientError },
                { clientResponse: ClientResponse? ->
                    Mono.error(
                        ValidationException("잘못된 입력")
                    )
                })
            .onStatus(
                { obj: HttpStatus -> obj.is5xxServerError },
                { clientResponse: ClientResponse? ->
                    Mono.error(
                        ValidationException("카카오 api 시도중 애러")
                    )
                })
            .bodyToMono(KakaoAccessTokenResponse::class.java)
            .block()
    }

    override fun getUserInfo(accessToken: String): KaKaoUserInfoResponse? {
        return WebClient.create()
            .get()
            .uri(kakaoAuthComponent.userInfoUrl)
            .headers(Consumer { headers: HttpHeaders ->
                headers.setBearerAuth(
                    accessToken
                )
            })
            .retrieve()
            .onStatus(
                { obj: HttpStatus -> obj.is4xxClientError },
                { clientResponse: ClientResponse? ->
                    Mono.error(
                        ValidationException("잘못된 입력")
                    )
                })
            .onStatus(
                { obj: HttpStatus -> obj.is5xxServerError },
                { clientResponse: ClientResponse? ->
                    Mono.error(
                        ValidationException("카카오 api 시도중 애러")
                    )
                })
            .bodyToMono(KaKaoUserInfoResponse::class.java)
            .block()
    }

}