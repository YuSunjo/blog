import org.springframework.boot.gradle.tasks.bundling.BootJar

extra["querydslVersion"] = "5.0.0"

plugins {
    id("kotlin")
    kotlin("kapt")
    kotlin("plugin.jpa")
}

allOpen {
    annotation("jakarta.persistence.Entity")
    annotation("jakarta.persistence.Embeddable")
    annotation("jakarta.persistence.MappedSuperclass")
    annotation("jakarta.persistence.EntityListeners")
    annotation("org.springframework.data.annotation.CreatedDate")
    annotation("org.springframework.data.jpa.domain.support.AuditingEntityListener")
}

dependencies {
    implementation(project(":blog-common"))

    val kapt by configurations
    api("org.springframework.boot:spring-boot-starter-data-jpa")
    api("com.querydsl:querydsl-jpa:${property("querydslVersion")}:jakarta")
    kapt("com.querydsl:querydsl-apt:${property("querydslVersion")}:jakarta")
    kapt("org.springframework.boot:spring-boot-configuration-processor")
    implementation("com.github.gavlyukovskiy:p6spy-spring-boot-starter:1.8.0")

    kapt ("jakarta.annotation:jakarta.annotation-api")
    kapt ("jakarta.persistence:jakarta.persistence-api")

    runtimeOnly("com.h2database:h2")
}

// Kotlin QClass Setting
kotlin.sourceSets.main {
    println("kotlin sourceSets builDir:: $buildDir")
    setBuildDir("$buildDir")
}

tasks.withType<Jar> {
    enabled = true
}

tasks.withType<BootJar> {
    enabled = false
}