package com.study.kopringchat.global.common

import com.fasterxml.jackson.annotation.JsonInclude
import org.springframework.http.HttpStatus

@JsonInclude(JsonInclude.Include.NON_NULL)
data class BaseResponse<T>(
    val status: Int,
    val state: String? = null,
    val message: String,
    val data: T? = null
) {
    constructor(status: HttpStatus, state: String? = null, message: String, data: T? = null) : this(
        status = status.value(),
        state = state,
        message = message,
        data = data
    )
}
