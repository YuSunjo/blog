package com.blog.config

import com.blog.BlogApiApplication
import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.context.annotation.Configuration

@EnableFeignClients(basePackageClasses = [BlogApiApplication::class])
@Configuration
class FeignClientConfig {
}