package org.example.config;

import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI().info(new Info()
        .title("Library API")
        .description("MVP: Members, Catalog, Search, Loans, Reservations/Fines")
        .version("v1"));
    }
}
