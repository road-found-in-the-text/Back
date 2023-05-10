package com.example.Back.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;



@OpenAPIDefinition(
        info = @Info(
                title = "road on the text ",
                description = "API 명세서",
                version = "v1"
        )
)

@Configuration
public class SwaggerConfig {

}