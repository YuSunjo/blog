package com.blog.service.member

import com.blog.exception.ValidationException
import org.springframework.security.crypto.password.PasswordEncoder

class MemberServiceUtils {

    companion object {
        fun validatePassword(passwordEncoder: PasswordEncoder, encodedPassword: String, password: String) {
            if (!passwordEncoder.matches(password, encodedPassword)) {
                throw ValidationException("알맞지 않은 비밀번호입니다.")
            }
        }
    }

}
