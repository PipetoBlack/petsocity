package com.petsocity.petsocity.config;

import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import io.swagger.v3.oas.models.tags.Tag;

import java.util.List;

import org.springframework.context.annotation.Bean;


@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI(){
        return new OpenAPI()
            .info(new Info()
                .title("API 2025 Usuarios")
                .version("1.0")
                .description("Documentación de la API para el sistema de usuarios de Petsocity.")
                .contact(new Contact()
                    .name("Equipo Petsocity")
                    .url("https://github.com/PipetoBlack")
                    .email("support@petsocity.cl"))
                .license(new License()
                    .name("MIT License")
                    .url("https://opensource.org/licenses/MIT")))
            .servers(List.of(
                new Server().url("http://localhost:8088").description("Servidor local"),
                new Server().url("https://api.petsocity.cl").description("Servidor de producción")
            ))
            .components(new Components()
                .addSecuritySchemes("BearerAuth", 
                    new SecurityScheme()
                        .type(SecurityScheme.Type.HTTP)
                        .scheme("bearer")
                        .bearerFormat("JWT")
                        .in(SecurityScheme.In.HEADER)
                        .name("Authorization")))
            .addSecurityItem(new SecurityRequirement().addList("BearerAuth"))
            .tags(List.of(
                new Tag().name("Usuarios").description("Operaciones CRUD de usuarios")
            ));

    }
}
