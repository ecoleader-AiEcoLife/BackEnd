package com.example.practice_eddy.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    /**
     * OpenAPI bean 구성
     *
     * @return
     */
    @Bean
    public OpenAPI openAPI() {
        Info info = new Info()
            .title("Eddy API")
            .version("1.0")
            .description("분리수거 정보 제공 Ai 서비스 및 지도 서비스");
        return new OpenAPI()
            .components(new Components())
            .info(info);
    }
}