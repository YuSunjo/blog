package com.blog.exception.controllerAdvice

import com.blog.ErrorResponse
import com.blog.exception.ConflictException
import com.blog.exception.JwtException
import com.blog.exception.NotFoundException
import com.blog.exception.ValidationException
import com.blog.exception.errorCode.ErrorCode
import io.sentry.Sentry
import org.springframework.http.HttpStatus
import org.springframework.validation.BindException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ExceptionController {

    @ExceptionHandler(value = [NotFoundException::class])
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun handleNotFound(e: NotFoundException): ErrorResponse {
        Sentry.captureException(e)
        return ErrorResponse.error(e.errorCode, e.localizedMessage)
    }

    @ExceptionHandler(value = [ConflictException::class])
    @ResponseStatus(HttpStatus.CONFLICT)
    fun handlerConflict(e: ConflictException): ErrorResponse {
        Sentry.captureException(e)
        return ErrorResponse.error(e.errorCode, e.localizedMessage)
    }

    @ExceptionHandler(value = [JwtException::class])
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    fun handlerJwt(e: JwtException): ErrorResponse {
        Sentry.captureException(e)
        return ErrorResponse.error(e.errorCode, e.localizedMessage)
    }

    @ExceptionHandler(value = [ValidationException::class])
    @ResponseStatus(HttpStatus.CONFLICT)
    fun handlerValidation(e: ValidationException): ErrorResponse {
        Sentry.captureException(e)
        return ErrorResponse.error(e.errorCode, e.localizedMessage)
    }

    @ExceptionHandler(value = [MethodArgumentNotValidException::class])
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handlerValidation(e: MethodArgumentNotValidException): ErrorResponse {
        Sentry.captureException(e)
        return ErrorResponse.error(ErrorCode.VALIDATION_EXCEPTION, e.bindingResult.allErrors.get(0).defaultMessage)
    }

    @ExceptionHandler(value = [BindException::class])
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handlerValidation(e: BindException): ErrorResponse {
        Sentry.captureException(e)
        return ErrorResponse.error(ErrorCode.VALIDATION_EXCEPTION, e.localizedMessage)
    }
}