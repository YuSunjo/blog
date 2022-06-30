import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "2.7.0"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    kotlin("jvm") version "1.6.21"
    kotlin("kapt") version "1.6.21"
    kotlin("plugin.spring") version "1.6.21"
    kotlin("plugin.jpa") version "1.6.21"
    id("com.google.cloud.tools.jib") version "3.2.1"
//    id("jacoco")
    id("application")
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

application {
    mainClass.set("com.blog.BlogAdminApplication.kt")
    mainClass.set("com.blog.BlogApiApplication.kt")
}

subprojects {
    apply {
        plugin("kotlin")
//        plugin("org.jlleitschuh.gradle.ktlint")
        plugin("org.jetbrains.kotlin.jvm")
        plugin("org.springframework.boot")
        plugin("io.spring.dependency-management")
        plugin("org.jetbrains.kotlin.plugin.spring") // allOpen처리를 위해 모든 프로젝트에 kotlin-spring플러그인 적용
        plugin("application")
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