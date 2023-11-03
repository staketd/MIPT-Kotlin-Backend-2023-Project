
plugins {
    kotlin("jvm") version "1.9.10"
    id("io.ktor.plugin") version "2.3.5"
    kotlin("plugin.serialization") version "1.9.10"
    application
}

group = "edu.phystech.kotlin.backend"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("io.ktor:ktor-server-auth-jvm:2.3.5")
    implementation("io.ktor:ktor-server-core-jvm:2.3.5")
    implementation("io.ktor:ktor-server-auth-jwt-jvm:2.3.5")
    val ktorVersion = "2.3.5"
    val koinVersion = "3.5.0"
    val logbackVersion = "1.4.11"
    testImplementation(kotlin("test"))

    implementation("io.ktor:ktor-server-core:$ktorVersion")
    implementation("io.ktor:ktor-server-netty:$ktorVersion")

    implementation("io.ktor:ktor-server-content-negotiation:$ktorVersion")
    implementation("io.ktor:ktor-serialization-kotlinx-json:$ktorVersion")
    implementation("io.ktor:ktor-server-status-pages:$ktorVersion")

    implementation("io.ktor:ktor-server-auth:$ktorVersion")
    implementation("io.ktor:ktor-server-auth-jwt:$ktorVersion")

    implementation("io.insert-koin:koin-core:$koinVersion")
    implementation("io.insert-koin:koin-ktor:$koinVersion")

    // Ktor logging dependencies
    implementation("io.ktor:ktor-server-call-logging:$ktorVersion")
    implementation("ch.qos.logback:logback-classic:$logbackVersion")
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(17)
}

application {
    mainClass.set("edu.phystech.kotlin.backend.Application.kt")
}