package com.plagiarism.plagiarismremover.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

@OpenAPIDefinition
@Configuration
public class OpenApiConfig {   
	@Value("${bearer.security.scheme.name}")
	private String bearerSecuritySchemeName;
	
	@Value("${basic.security.scheme.name}")
	private String basicSecuritySchemeName;
    
	@Bean
    public OpenAPI baseOpenAPI(){
        return new OpenAPI()
                .info(new Info()
                        .title("Plagiarism Remover Swagger Project OpenAPI Docs")
                        .version("1.0.0")
                        .description("OpenAPI Docs Description")
                )
                .addSecurityItem(new SecurityRequirement().addList(bearerSecuritySchemeName).addList(basicSecuritySchemeName))
                .components(new Components()
                        .addSecuritySchemes(bearerSecuritySchemeName,
                                new SecurityScheme()
                                        .name(bearerSecuritySchemeName)
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT"))
                        .addSecuritySchemes(basicSecuritySchemeName,
                                new SecurityScheme()
                                        .name(basicSecuritySchemeName)
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("basic")
                        )
                );
    }
}