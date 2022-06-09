package com.blog

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@SpringBootApplication
@EnableJpaAuditing
class BlogAdminApplication

fun main(args: Array<String>) {
    runApplication<BlogAdminApplication>(*args)
}