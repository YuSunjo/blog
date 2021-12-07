package com.blog.config

import com.blog.config.argument.MemberIdArgumentResolver
import com.blog.config.auth.MemberInterceptor
import org.springframework.context.annotation.Configuration
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
open class WebConfig(
    private val memberInterceptor: MemberInterceptor,
    private val memberIdArgumentResolver: MemberIdArgumentResolver
) : WebMvcConfigurer {

    override fun addCorsMappings(registry: CorsRegistry) {
        registry.addMapping("/**")
            .allowedOrigins("*")
            .allowedMethods("GET", "POST", "PUT", "DELETE", "HEAD", "OPTIONS", "PATCH")
    }

    override fun addInterceptors(registry: InterceptorRegistry) {
        registry.addInterceptor(memberInterceptor)
    }

    override fun addArgumentResolvers(resolvers: MutableList<HandlerMethodArgumentResolver>) {
        resolvers.add(memberIdArgumentResolver)
    }

}