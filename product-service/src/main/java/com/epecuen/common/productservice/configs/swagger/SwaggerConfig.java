package com.epecuen.common.productservice.configs.swagger;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

/**
 * Swagger configuration class for the Epecuen User Service API.
 * It defines the API metadata such as title, version, description, and contact information.
 * This configuration is used to generate OpenAPI documentation for the service.
 *
 * @author caito
 *
 */
@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Epecuen Product Service API",
                version = "1.0.0",
                description = "Epecuen Inventory and stock management system",
                contact = @Contact(name = "caito Vilas", email = "caitocd@gmail.com")
        )
)
public class SwaggerConfig {
}
