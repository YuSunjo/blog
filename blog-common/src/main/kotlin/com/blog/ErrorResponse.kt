package com.blog

import com.blog.exception.errorCode.ErrorCode

data class ErrorResponse(
    val code:Int,
    val message: String,
    val description: String
) {
    companion object {
        fun error(errorCode: ErrorCode, description: String): ErrorResponse {
            return ErrorResponse(errorCode.code, errorCode.message, description)
        }
    }
}