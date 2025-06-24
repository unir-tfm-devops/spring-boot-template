package com.unir.template.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

  @Bean
  public OpenAPI customOpenAPI() {
    return new OpenAPI()
        .info(
            new Info()
                .title("Spring Boot Template")
                .description(
                    "A comprehensive REST API template built with Spring Boot 3.5.0 and Java 24. "
                        + "This template provides a solid foundation for building production-ready REST APIs "
                        + "with best practices for enterprise application development.")
                .version("1.0.0")
                .contact(
                    new Contact()
                        .name("Alvaro Rodriguez Gonzalez")
                        .email("alvaro.rodriguezgonzalez@gmail.com")
                        .url("https://github.com/unir-tfm-devops/spring-boot-template"))
                .license(
                    new License().name("MIT License").url("https://opensource.org/licenses/MIT")))
        .servers(
            List.of(
                new Server().url("http://localhost:8080").description("Local Development Server"),
                new Server().url("https://api.example.com").description("Production Server")))
        .components(
            new Components()
                .addSecuritySchemes(
                    "bearerAuth",
                    new io.swagger.v3.oas.models.security.SecurityScheme()
                        .type(io.swagger.v3.oas.models.security.SecurityScheme.Type.HTTP)
                        .scheme("bearer")
                        .bearerFormat("JWT")
                        .description(
                            "JWT Authorization header using the Bearer scheme. "
                                + "Enter 'Bearer' [space] and then your token in the text input below.")));
  }
}
