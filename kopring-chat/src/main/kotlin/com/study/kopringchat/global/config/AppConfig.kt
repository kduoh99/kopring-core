package com.study.kopringchat.global.config

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.client.RestClient

@Configuration
class AppConfig {

    @Bean
    fun passwordEncoder(): PasswordEncoder = BCryptPasswordEncoder()

    @Bean
    fun restClient(): RestClient = RestClient.builder().build()

    @Bean
    fun objectMapper(): ObjectMapper = ObjectMapper().findAndRegisterModules()
}
