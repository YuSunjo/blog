package com.blog.config.swagger

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpHeaders
import springfox.documentation.builders.ApiInfoBuilder
import springfox.documentation.builders.PathSelectors
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.service.*
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spi.service.contexts.SecurityContext
import springfox.documentation.spring.web.plugins.Docket
import springfox.documentation.swagger2.annotations.EnableSwagger2

@Configuration
@EnableSwagger2
class SwaggerConfig {
    @Bean
    fun restApi(): Docket {
        return Docket(DocumentationType.SWAGGER_2)
            .apiInfo(apiInfo())
            .select()
            .apis(RequestHandlerSelectors.basePackage("com.blog"))
            .paths(PathSelectors.any())
            .build()
            .securityContexts(listOf(securityContext()))
            .securitySchemes(listOf(apiKey()))
    }

    private fun apiInfo(): ApiInfo {
        return ApiInfoBuilder()
            .title("blog")
            .version("1.0.0")
            .description("blog")
            .contact(Contact("Contact", "https://github.com/YuSunjo", "tnswh2023@gmail.com"))
            .build()
    }

    private fun apiKey(): ApiKey {
        return ApiKey("JWT_AUTH", HttpHeaders.AUTHORIZATION, "header")
    }

    private fun securityContext(): SecurityContext {
        return SecurityContext
            .builder()
            .securityReferences(defaultAuth()).forPaths(PathSelectors.ant("/api/**")).build()
    }

    fun defaultAuth(): List<SecurityReference> {
        val authorizationScope = AuthorizationScope("/api/*", "accessEverything")
        val authorizationScopes = arrayOfNulls<AuthorizationScope>(1)
        authorizationScopes[0] = authorizationScope
        return listOf(SecurityReference("JWT_AUTH", authorizationScopes))
    }
}