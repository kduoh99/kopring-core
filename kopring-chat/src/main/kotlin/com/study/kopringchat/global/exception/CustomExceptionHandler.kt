package com.study.kopringchat.global.exception

import com.study.kopringchat.global.common.BaseResponse
import org.springframework.http.HttpStatus
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class CustomExceptionHandler {

    @ExceptionHandler(CustomException::class)
    fun handleCustomException(e: CustomException) = BaseResponse<Unit>(e.errorCode)

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleValidationException(e: MethodArgumentNotValidException): BaseResponse<Unit> {
        val errorMessage = e.bindingResult.fieldErrors.firstOrNull()?.defaultMessage ?: "잘못된 요청입니다."

        return BaseResponse(
            status = HttpStatus.BAD_REQUEST,
            state = "INVALID",
            message = errorMessage
        )
    }

    @ExceptionHandler(Exception::class)
    fun handleException(e: Exception) =
        BaseResponse<Unit>(
            status = HttpStatus.INTERNAL_SERVER_ERROR,
            message = e.message ?: "서버 내부 오류가 발생했습니다."
        )
}
