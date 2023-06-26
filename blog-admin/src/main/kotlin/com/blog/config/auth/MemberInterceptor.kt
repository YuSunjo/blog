package com.blog.config.auth

import com.blog.exception.ValidationException
import org.springframework.stereotype.Component
import org.springframework.web.method.HandlerMethod
import org.springframework.web.servlet.HandlerInterceptor
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse

@Component
class MemberInterceptor(
    private val memberComponent: MemberComponent
) : HandlerInterceptor {
    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        if (handler !is HandlerMethod) {
            return true
        }
        handler.getMethodAnnotation(Member::class.java) ?: return true
        if (!memberComponent.authCheck(request)) {
            throw ValidationException("잘못된 토큰입니다.")
        }
        return true
    }
}