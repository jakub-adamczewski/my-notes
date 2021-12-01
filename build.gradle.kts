import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "2.5.5"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    kotlin("jvm") version "1.5.31"
    kotlin("plugin.spring") version "1.5.31"
    kotlin("plugin.jpa") version "1.5.31"
    kotlin("plugin.allopen") version "1.4.32"
    id("com.google.cloud.tools.jib") version "3.1.4"
    id("org.flywaydb.flyway") version "8.0.4"
    id("com.palantir.docker") version "0.31.0"
}

docker {
    name = "my_notes_app"
    setDockerfile(file("docker/Dockerfile"))
    copySpec.from("build/libs/my-notes.jar")
    buildArgs(mapOf("JAR_FILE" to "my-notes.jar"))
}

jib {
    to.image = "jadam5/my_notes"
    container.jvmFlags = listOf("-Dspring.profiles.active=prod")
}

allOpen {
    annotation("javax.persistence.Entity")
    annotation("javax.persistence.Embeddable")
    annotation("javax.persistence.MappedSuperclass")
}

group = "com.example"
version = ""
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-jpa:2.5.5")
    implementation("com.google.api-client:google-api-client:1.32.1")
    implementation("org.springframework.security:spring-security-core")
//    implementation("org.springframework.security:spring-security-access")
    implementation("org.springframework.security:spring-security-config")
    implementation("org.springframework.security:spring-security-web")
    implementation("org.springframework.boot:spring-boot-actuator-autoconfigure")
//Thanks for using https://jar-download.com

    implementation("org.zalando:problem-spring-web:0.27.0")

    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("com.google.firebase:firebase-admin:8.1.0")
    implementation("junit:junit:4.13.1")
    runtimeOnly("org.postgresql:postgresql:42.3.1")
    runtimeOnly("com.h2database:h2:1.4.200")
    testImplementation("org.springframework.boot:spring-boot-starter-test") {
        exclude(module = "mockito-core")
    }
    implementation("org.flywaydb:flyway-core")

    testImplementation("io.mockk:mockk:1.12.0")
    testImplementation("com.ninja-squad:springmockk:3.0.1")
    testImplementation("io.zonky.test:embedded-database-spring-test:2.0.1")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "11"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
