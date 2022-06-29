import org.springframework.boot.gradle.tasks.bundling.BootJar

extra["querydslVersion"] = "5.0.0"

plugins {
    id("kotlin")
    kotlin("kapt")
    kotlin("plugin.jpa")
}

allOpen {
    annotation("javax.persistence.Entity")
    annotation("javax.persistence.Embeddable")
    annotation("javax.persistence.MappedSuperclass")
    annotation("javax.persistence.EntityListeners")
    annotation("org.springframework.data.annotation.CreatedDate")
    annotation("org.springframework.data.jpa.domain.support.AuditingEntityListener")
}

dependencies {
    implementation(project(":blog-common"))

    val kapt by configurations
    api("org.springframework.boot:spring-boot-starter-data-jpa")
    api("com.querydsl:querydsl-jpa")
    kapt("com.querydsl:querydsl-apt::jpa")
    kapt("org.springframework.boot:spring-boot-configuration-processor")
    implementation("com.github.gavlyukovskiy:p6spy-spring-boot-starter:1.8.0")

    runtimeOnly("com.h2database:h2")
}

tasks.withType<Jar> {
    enabled = true
}

tasks.withType<BootJar> {
    enabled = false
}