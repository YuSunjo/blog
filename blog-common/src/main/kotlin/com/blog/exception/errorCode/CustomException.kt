package com.blog.exception.errorCode

open class CustomException(
    val errorCode: ErrorCode,
    override val message: String
) : RuntimeException() {
}