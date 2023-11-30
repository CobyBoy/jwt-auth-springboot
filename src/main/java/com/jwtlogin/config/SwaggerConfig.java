package com.jwtlogin.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.Collections;

@Configuration
public class SwaggerConfig {
    @Value("${application.version}")
    private String appVersion;

    @Bean
    public OpenAPI customOpenAPI() {
        String title = "Security Rest Service";
        String scheme = "bearer";
        String bearerFormat = "JWT";
        String key = "bearer-key";
        Info info = new Info().title(title).version(appVersion);
        SecurityScheme securitySchema = new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme(scheme)
                .bearerFormat(bearerFormat);
        Components components = new Components().addSecuritySchemes(key, securitySchema);
        SecurityRequirement securityRequirements = new SecurityRequirement()
                .addList("bearer-jwt", Arrays.asList("read", "write"))
                .addList(key, Collections.emptyList());
        return new OpenAPI().info(info).components(components).addSecurityItem(securityRequirements);
    }
}
