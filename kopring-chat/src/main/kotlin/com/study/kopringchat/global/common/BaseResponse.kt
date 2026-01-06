package com.study.kopringchat.global.common

import com.fasterxml.jackson.annotation.JsonInclude
import com.study.kopringchat.global.exception.CustomErrorCode
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

class BaseResponse<T>(
    status: HttpStatus,
    state: String? = null,
    message: String,
    data: T? = null
) : ResponseEntity<BaseResponse.Body<T>>(
    Body(
        status = status.value(),
        state = if (status.is2xxSuccessful) null else (state ?: "ERROR"),
        message = message,
        data = data
    ),
    status
) {
    constructor(errorCode: CustomErrorCode) : this(
        status = errorCode.status,
        state = errorCode.state,
        message = errorCode.message
    )

    @JsonInclude(JsonInclude.Include.NON_NULL)
    data class Body<T>(
        val status: Int,
        val state: String?,
        val message: String,
        val data: T?
    )
}
