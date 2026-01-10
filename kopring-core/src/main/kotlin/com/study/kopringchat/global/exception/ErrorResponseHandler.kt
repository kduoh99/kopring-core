package com.study.kopringchat.global.exception

import com.fasterxml.jackson.databind.ObjectMapper
import com.study.kopringchat.global.common.BaseResponse
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import java.nio.charset.StandardCharsets

@Component
class ErrorResponseHandler(
    private val objectMapper: ObjectMapper
) {
    fun handle(response: HttpServletResponse, errorCode: CustomErrorCode) {
        response.status = errorCode.status.value()
        response.contentType = MediaType.APPLICATION_JSON_VALUE
        response.characterEncoding = StandardCharsets.UTF_8.name()
        response.writer.write(
            objectMapper.writeValueAsString(
                BaseResponse<Unit>(
                    status = errorCode.status,
                    state = errorCode.state,
                    message = errorCode.message
                )
            )
        )
    }
}
