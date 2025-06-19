package com.petsocity.petsocity.config;

import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;


@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI(){
        return new OpenAPI().info(new Info()
            .title("API 2025 Usuarios")
            .version("1.0")
            .description("Documentacion de la API para el sistema de usuarios"));
    }
}
