package com.blog.config.auth

import com.blog.config.jwt.JwtTokenProvider
import com.blog.domain.admin.repository.AdminRepository
import com.blog.exception.NotFoundException
import com.blog.exception.ValidationException
import org.springframework.http.HttpHeaders
import org.springframework.stereotype.Component
import org.springframework.util.StringUtils
import javax.servlet.http.HttpServletRequest

@Component
class MemberComponent(
    private val jwtTokenProvider: JwtTokenProvider,
    private val adminRepository: AdminRepository
) {
    fun authCheck(request: HttpServletRequest): Boolean {
        val header = request.getHeader(HttpHeaders.AUTHORIZATION)
        if (!StringUtils.hasLength(header)) {
            throw ValidationException("잘못된 헤더 ${header} 입니다.")
        }

        val memberId = jwtTokenProvider.getSubject(header)
        if (memberId != null) {
            adminRepository.findById(memberId.toLong())
                ?: throw NotFoundException("존재하지 않는 멤버 아이디 ${memberId} 입니다")
        }
        if (memberId != null) {
            request.setAttribute("memberId", memberId.toLong())
        }
        return true
    }

}