package com.azo.ecommerce.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import io.swagger.v3.oas.models.servers.Server;

import java.util.List;

public class SwaggerConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("E-Commerce API")
                        .version("v1.0")
                        .description("Complete E-Commerce REST API with Spring Boot and PostgreSQL")
                        .contact(new Contact()
                                .name("Andrija")
                                .email("andrija.azo233@gmail.com"))
                        .license(new License()
                                .name("MIT License")
                                .url("https://opensource.org/licenses/MIT")))
                .servers(List.of(
                        new Server().url("http://localhost:8080").description("Development Server"),
                        new Server().url("https://your-production-url.com").description("Production Server")
                ));
    }
}
