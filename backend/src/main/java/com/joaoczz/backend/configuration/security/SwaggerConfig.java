package com.joaoczz.backend.configuration.security;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.security.SecuritySchemes;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Music Backend API",
                version = "1.0",
                description = "Backend del proyecto musical de Joao Cozzarelli"
        ),
        // Aplica ambos esquemas globalmente a todos los endpoints
        security = {
                @SecurityRequirement(name = "bearerAuth"),
                @SecurityRequirement(name = "basicAuth")
        }
)
@SecuritySchemes({
        // Autenticación con JWT (pegar el token devuelto por /auth/log-in)
        @SecurityScheme(
                name = "bearerAuth",
                type = SecuritySchemeType.HTTP,
                scheme = "bearer",
                bearerFormat = "JWT",
                description = "Introduce el JWT obtenido de POST /auth/log-in"
        ),
        // Autenticación con usuario y contraseña directamente
        @SecurityScheme(
                name = "basicAuth",
                type = SecuritySchemeType.HTTP,
                scheme = "basic",
                description = "Introduce tu usuario y contraseña"
        )
})
public class SwaggerConfig {}

