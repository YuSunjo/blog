package com.blog

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class BlogAdminApplication

fun main(args: Array<String>) {
    runApplication<BlogAdminApplication>(*args)
}