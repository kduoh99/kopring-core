package com.study.kopringchat.global.exception

import com.study.kopringchat.global.common.BaseResponse
import org.springframework.http.HttpStatus
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class CustomExceptionHandler {

    @ExceptionHandler(CustomException::class)
    fun handleCustomException(e: CustomException): BaseResponse<Unit> {
        return BaseResponse(e.errorCode.status, e.errorCode.state, e.errorCode.message)
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleValidation(e: MethodArgumentNotValidException): BaseResponse<Unit> {
        val message = e.bindingResult.fieldErrors
            .joinToString(", ") { "${it.field}: ${it.defaultMessage}" }

        return BaseResponse(HttpStatus.BAD_REQUEST, "BAD_REQUEST", message)
    }
}
