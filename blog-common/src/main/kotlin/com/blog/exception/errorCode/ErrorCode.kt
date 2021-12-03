package com.blog.exception.errorCode

enum class ErrorCode(
    private val code: Int,
    private val message: String
) {

    NOT_FOUND_EXCEPTION(404, "존재하지 않습니다."),
    JWT_UNAUTHORIZED_EXCEPTION(403, "인증애러입니다."),
    CONFLICT_EXCEPTION(409, "이미 존재합니다."),
    VALIDATION_EXCEPTION(400, "잘못된 입력입니다.")

}