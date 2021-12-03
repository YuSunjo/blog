package com.blog.exception.errorCode

open class CustomException(
    private val errorCode: ErrorCode,
    override val message: String
) : RuntimeException() {
}