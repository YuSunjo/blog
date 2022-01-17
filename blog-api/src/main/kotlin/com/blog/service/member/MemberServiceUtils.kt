package com.blog.service.member

import com.blog.domain.member.repository.MemberRepository
import com.blog.exception.ValidationException
import org.springframework.security.crypto.password.PasswordEncoder

class MemberServiceUtils {

    companion object {
        fun validateEmail(memberRepository: MemberRepository, email: String) {
            val validateEmail = memberRepository.findMemberByEmail(email)
            if (validateEmail != null) {
                throw ValidationException("${email}은 이미 존재하는 이메일입니다.")
            }
        }

        fun validatePassword(passwordEncoder: PasswordEncoder, encodedPassword: String, password: String) {
            if (!passwordEncoder.matches(password, encodedPassword)) {
                throw ValidationException("알맞지 않은 비밀번호입니다.")
            }
        }

        fun validateNickname(memberRepository: MemberRepository, nickname: String) {
            val member = memberRepository.findMemberByNickname(nickname)
            if (member != null) {
                throw ValidationException("${nickname}은 이미 존재하는 닉네임입니다.")
            }
        }

    }

}