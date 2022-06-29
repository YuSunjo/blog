import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "2.7.0"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    kotlin("jvm") version "1.6.21"
    kotlin("kapt") version "1.6.21"
    kotlin("plugin.spring") version "1.6.21"
    kotlin("plugin.jpa") version "1.6.21"
    id("com.google.cloud.tools.jib") version "3.2.1"
    id("jacoco")
//    id("org.jlleitschuh.gradle.ktlint") version "10.2.1"
}

repositories {
    mavenCentral()
    maven { url = uri("https://repo.spring.io/milestone") }
}

java.sourceCompatibility = JavaVersion.VERSION_11

extra["springCloudVersion"] = "2021.0.3"
extra["querydslPluginVersion"] = "1.0.10"
extra["kotestVersion"] = "5.1.0"
extra["mockkVersion"] = "1.11.0"

// jacoco {
//    toolVersion = "0.8.7"
// }

subprojects {
    apply {
        plugin("kotlin")
//        plugin("org.jlleitschuh.gradle.ktlint")
        plugin("org.jetbrains.kotlin.jvm")
        plugin("org.springframework.boot")
        plugin("io.spring.dependency-management")
        plugin("org.jetbrains.kotlin.plugin.spring") // allOpen처리를 위해 모든 프로젝트에 kotlin-spring플러그인 적용
    }

    group = "com.blog"
    version = "0.0.1-BLOG"

    repositories {
        mavenCentral()
    }

    dependencyManagement {
        imports {
            mavenBom("org.springframework.cloud:spring-cloud-dependencies:${property("springCloudVersion")}")
        }
    }

    dependencies {
        val implementation by configurations
        val testImplementation by configurations
        implementation("org.jetbrains.kotlin:kotlin-reflect")
        implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

        testImplementation("com.ninja-squad:springmockk:3.1.0")
        testImplementation("org.springframework.boot:spring-boot-starter-test") {
            exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
            exclude(module = "mockito-core")
        }

        implementation("org.springframework.boot:spring-boot-starter-data-jpa")

        // 기존꺼
        implementation("org.springframework.boot:spring-boot-starter-web")
        compileOnly("org.projectlombok:lombok")
        developmentOnly("org.springframework.boot:spring-boot-devtools")

        annotationProcessor("org.projectlombok:lombok")
        testImplementation("org.springframework.boot:spring-boot-starter-test")
        implementation("org.springframework.boot:spring-boot-starter-validation")
        implementation("org.springframework.boot:spring-boot-starter-oauth2-client")
        implementation("org.springframework.boot:spring-boot-starter-webflux")

        // maria db
        implementation("org.mariadb.jdbc:mariadb-java-client:2.7.5")

        // aop
        implementation("org.springframework.boot:spring-boot-starter-aop")

        implementation("com.fasterxml.jackson.module:jackson-module-kotlin")

        // log4j 취약점 때문에 업그레이드
        implementation("org.apache.logging.log4j:log4j-api:2.17.0")
        implementation("org.apache.logging.log4j:log4j-to-slf4j:2.17.0")
        implementation("org.apache.logging.log4j:log4j-core:2.17.0")

        annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")

        // swagger
        implementation("io.springfox:springfox-boot-starter:3.0.0")
        implementation("io.springfox:springfox-swagger-ui:3.0.0")

        // sentry
        implementation("io.sentry:sentry-spring-boot-starter:5.6.1")
        implementation("io.sentry:sentry-logback:5.6.1")

        implementation("org.springframework.cloud:spring-cloud-starter-openfeign")
        implementation("io.github.openfeign:feign-core:11.0")

        testImplementation("org.junit.jupiter:junit-jupiter:5.8.1")
        testImplementation("org.testcontainers:junit-jupiter:1.16.3")
        testImplementation("org.testcontainers:mysql:1.16.3")

        // kotest
        testImplementation("io.kotest:kotest-runner-junit5-jvm:5.1.0")
        testImplementation("io.kotest:kotest-assertions-core-jvm:5.1.0")

        // mockk
        testImplementation("io.mockk:mockk:1.11.0")
    }

    configure<JavaPluginExtension> {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    tasks.withType<KotlinCompile> {
//        dependsOn("ktlintCheck")
        kotlinOptions {
            freeCompilerArgs = listOf("-Xjsr305=strict")
            jvmTarget = JavaVersion.VERSION_11.toString()
        }
    }

//    tasks.jacocoTestReport {
//        reports {
//            html.isEnabled = true
//            xml.isEnabled = true
//            csv.isEnabled = false
//        }
//    }

    tasks.withType<Test> {
        useJUnitPlatform()
    }

//    tasks.test {
//        extensions.configure(JacocoTaskExtension::class) {
//            destinationFile = file("$buildDir/jacoco/jacoco.exec")
//        }
//        finalizedBy("jacocoTestReport")
//    }
}

// buildscript {
//    ext {
//        kotlinVersion = "1.5.21"
//        springBootVersion = "2.7.0"
//        querydslPluginVersion ="1.0.10"
//        springCloudVersion = "2021.0.3"
//        testcontainersVersion = "1.14.3"
//        KOTEST_VERSION = "5.1.0"
//        MOCKK_VERSION = "1.11.0"
//    }
//    repositories {
//        mavenCentral({
//            url "https://plugins.gradle.org/m2/"
//        })
//    }
//    dependencies {
//        classpath("org.springframework.boot:spring-boot-gradle-plugin:${springBootVersion}")
//        classpath("io.spring.gradle:dependency-management-plugin:1.0.11.RELEASE")
//        classpath("gradle.plugin.com.ewerk.gradle.plugins:querydsl-plugin:${querydslPluginVersion}")
//        classpath("org.jetbrains.kotlin:kotlin-allopen:${kotlinVersion}")
//        classpath("org.jetbrains.kotlin:kotlin-noarg:${kotlinVersion}")
//        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:${kotlinVersion}")
//        classpath("org.jlleitschuh.gradle:ktlint-gradle:9.1.0")
//    }
// }
//
// plugins {
//    id "org.sonarqube" version "3.3"
//    id "jacoco"
//    id "org.jlleitschuh.gradle.ktlint" version "9.1.0"
// }
//
// subprojects {
//    apply plugin: "java"
//    apply plugin: "org.springframework.boot"
//    apply plugin: "io.spring.dependency-management"
//    apply plugin: "java-library"
//    apply plugin: "kotlin"
//    apply plugin: "kotlin-spring"
//    apply plugin: "kotlin-jpa"
//    apply plugin: "org.jetbrains.kotlin.plugin.spring"
//    apply plugin: "org.jetbrains.kotlin.jvm"
//    apply plugin: "org.jetbrains.kotlin.kapt"
//    apply plugin: "jacoco"
//
//    group = "com.blog"
//    version = "0.0.1-BLOG"
//    sourceCompatibility = 11
//
//    repositories {
//        mavenCentral()
//    }
//
//    configurations {
//        compileOnly {
//            extendsFrom annotationProcessor
//        }
//    }
//
//    dependencyManagement {
//        imports {
//            mavenBom "org.springframework.cloud:spring-cloud-dependencies:${springCloudVersion}"
//            mavenBom("org.testcontainers:testcontainers-bom:${testcontainersVersion}")
//        }
//    }
//
//    dependencies {
//        implementation "org.springframework.boot:spring-boot-starter-data-jpa"
//        implementation "org.springframework.boot:spring-boot-starter-web"
//        compileOnly "org.projectlombok:lombok"
//        developmentOnly "org.springframework.boot:spring-boot-devtools"
//
//        annotationProcessor "org.projectlombok:lombok"
//        testImplementation "org.springframework.boot:spring-boot-starter-test"
//        implementation "org.springframework.boot:spring-boot-starter-validation"
//        implementation "org.springframework.boot:spring-boot-starter-oauth2-client"
//        implementation "org.springframework.boot:spring-boot-starter-webflux"
//
//        // maria db
//        implementation group: "org.mariadb.jdbc", name: "mariadb-java-client", version: "2.4.1"
//
//        // aop
//        implementation "org.springframework.boot:spring-boot-starter-aop"
//
//        implementation("org.jetbrains.kotlin:kotlin-stdlib")
//        implementation("org.jetbrains.kotlin:kotlin-reflect")
//        implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
//
//        // log4j 취약점 때문에 업그레이드
//        implementation "org.apache.logging.log4j:log4j-api:2.17.0"
//        implementation "org.apache.logging.log4j:log4j-to-slf4j:2.17.0"
//        implementation "org.apache.logging.log4j:log4j-core:2.17.0"
//
//        annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
//
//        // swagger
//        implementation "io.springfox:springfox-boot-starter:3.0.0"
//        implementation "io.springfox:springfox-swagger-ui:3.0.0"
//
//        // sentry
//        implementation "io.sentry:sentry-spring-boot-starter:5.6.1"
//        implementation "io.sentry:sentry-logback:5.6.1"
//
//        implementation "org.springframework.cloud:spring-cloud-starter-openfeign"
//        implementation group: "io.github.openfeign", name: "feign-gson", version: "11.0"
//
//        testImplementation "org.junit.jupiter:junit-jupiter:5.8.1"
//
//        // kotest
//        testImplementation("io.kotest:kotest-runner-junit5-jvm:${KOTEST_VERSION}")
//        testImplementation("io.kotest:kotest-assertions-core-jvm:${KOTEST_VERSION}")
//        runtimeOnly("io.kotest:kotest-framework-engine-jvm:${KOTEST_VERSION}")
//
//        // mockk
//        testImplementation("io.mockk:mockk:${MOCKK_VERSION}")
//    }
//
//    sonarqube {
//        properties {
//            property "sonar.projectKey", "YuSunjo_blog"
//            property "sonar.organization", "petitionsonar"
//            property "sonar.host.url", "https://sonarcloud.io"
//            property "sonar.java.coveragePlugin", "jacoco"
//            property "sonar.coverage.jacoco.xmlReportPaths", "$buildDir/reports/jacoco/test/jacocoTestReport.xml"
//        }
//    }
//
//    jacoco {
//        toolVersion = "0.8.7"
//    }
//
//    jacocoTestReport {
//        reports {
//            html.enabled true
//            xml.enabled true
//            csv.enabled false
//        }
//    }
//
//    tasks.test {
//        jacoco {
//            destinationFile = file("$buildDir/jacoco/jacoco.exec")
//        }
//        useJUnitPlatform()
//        finalizedBy jacocoTestReport
//    }
// }
//
// project(":blog-admin") {
//    bootJar.enabled = true
// }
//
// project(":blog-api") {
//    bootJar.enabled = true
// }
//
// project(":blog-domain") {
//    bootJar.enabled = false
//    jar.enabled = true
// }
//
// project(":blog-common") {
//    bootJar.enabled = false
//    jar.enabled = true
// }