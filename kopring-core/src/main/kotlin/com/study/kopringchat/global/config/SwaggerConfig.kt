package com.study.kopringchat.global.config

import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.security.SecurityRequirement
import io.swagger.v3.oas.models.security.SecurityScheme
import io.swagger.v3.oas.models.servers.Server
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class SwaggerConfig {

    @Bean
    fun openAPI(): OpenAPI {
        val securitySchemeName = "BearerAuth"

        return OpenAPI()
            .info(Info().apply {
                title = "Chat Service API"
                description = "Chat Service API 명세서"
                version = "1.0.0"
            })
            .addServersItem(Server().apply {
                url = "http://localhost:8080"
                description = "Local Server"
            })
            .components(Components().addSecuritySchemes(securitySchemeName,
                SecurityScheme().apply {
                    name = securitySchemeName
                    type = SecurityScheme.Type.HTTP
                    scheme = "bearer"
                    bearerFormat = "JWT"
                }
            ))
            .addSecurityItem(SecurityRequirement().addList(securitySchemeName))
    }
}
