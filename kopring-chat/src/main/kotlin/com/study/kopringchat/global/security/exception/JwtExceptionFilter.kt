package com.study.kopringchat.global.security.exception

import com.study.kopringchat.global.exception.CustomException
import com.study.kopringchat.global.exception.ErrorResponse
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class JwtExceptionFilter(
    private val errorResponse: ErrorResponse
) : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        try {
            filterChain.doFilter(request, response)
        } catch (e: CustomException) {
            errorResponse.send(response, e.errorCode)
        }
    }
}
