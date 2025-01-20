package com.test.dux.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(info = @Info(
        title = "DUX-SOFTWARE",
        description = "Api desarrollada en base a la prueba tecnica de dux software.",
        version = "1.0",
        contact = @Contact(
                name = "Carlos Mamani",
                email = "carlosferandodeveloper@gmail.com",
                url = "https://github.com/CarlosCommit"
        )
))
@Configuration
public class SwaggerConfig {
}