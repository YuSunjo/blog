package com.blog.exception

import com.blog.exception.errorCode.CustomException
import com.blog.exception.errorCode.ErrorCode

data class NotFoundException(
    override val message: String,
) : CustomException(ErrorCode.NOT_FOUND_EXCEPTION, message)

data class ConflictException(
    override val message: String,
) : CustomException(ErrorCode.CONFLICT_EXCEPTION, message)

data class JwtException(
    override val message: String,
) : CustomException(ErrorCode.JWT_UNAUTHORIZED_EXCEPTION, message)

data class ValidationException(
    override val message: String,
) : CustomException(ErrorCode.VALIDATION_EXCEPTION, message)