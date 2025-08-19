package com.eprcuen.userservice.configs.swagger;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;

/**
 * Swagger configuration class for the Chemical.co User Service.
 * This class sets up the OpenAPI documentation for the application,
 * including API information and security schemes.
 *
 * @author caito
 *
 */
@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Epecuen / User Services",
                version = "1.0.0",
                description = "Epecuen User Service API Documentation",
                contact = @Contact(name = "caito Vilas", email = "caitocd@gmail.com")
        )
)
@SecurityScheme(
        name = "security token",
        type = SecuritySchemeType.HTTP,
        paramName = HttpHeaders.AUTHORIZATION,
        in = SecuritySchemeIn.HEADER,
        scheme = "Bearer",
        bearerFormat = "JWT"
)
public class SwaggerConfig {
}
