package com.example.Back.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI openAPI() {
        Info info = new Info()
                .title("글길 Phase1")
                .version("v1.0.0")
                .description("글에서 발견한 길 프로젝트 API 명세서");
        return new OpenAPI()
                .components(new Components())
                .info(info);
    }
}

