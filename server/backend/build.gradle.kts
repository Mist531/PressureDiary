plugins {
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.ktor)
    alias(libs.plugins.kotlin.jvm)
}

group = "io.pressurediary.server.backend"
version = "0.0.1"

application {
    mainClass.set("io.pressurediary.server.backend.ApplicationKt")

    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

dependencies {
    implementation(project(":server:api"))

    testImplementation(libs.ktor.server.test.host)

    implementation(libs.ktor.server.status.pages)
    implementation(libs.ktor.server.core.jvm)
    implementation(libs.ktor.server.auth.jvm)
    implementation(libs.ktor.server.auth.jwt.jvm)
    implementation(libs.ktor.server.resources)
    implementation(libs.ktor.server.cors.jvm)
    implementation(libs.ktor.server.openapi)
    implementation(libs.ktor.server.swagger.jvm)
    implementation(libs.ktor.server.call.logging.jvm)
    implementation(libs.ktor.server.content.negotiation.jvm)
    implementation(libs.ktor.server.data.conversion)
    implementation(libs.ktor.serialization.kotlinx.json.jvm)
    implementation(libs.ktor.serialization.gson.jvm)

    testImplementation(libs.ktor.client.content.negotiation)
    testImplementation(libs.h2)
    testImplementation(libs.ktor.client.logging)
    implementation(libs.bcrypt)

    implementation(libs.exposed.core)
    implementation(libs.exposed.dao)
    implementation(libs.exposed.jdbc)
    implementation(libs.exposed.java.time)
    implementation(libs.ktor.server.netty.jvm)
    implementation(libs.logback.classic)
    testImplementation(libs.ktor.server.tests.jvm)
    testImplementation(libs.kotlin.test.junit)

    //region Koin
    implementation(libs.koin.ktor)
    implementation(libs.koin.logger.slf4j)
    //endregion

    //region BD
    implementation(libs.postgresql)
    //endregion

    //region Arrow
    implementation(platform(libs.arrow.stack))
    implementation(libs.arrow.core)
    implementation(libs.arrow.optics)
    implementation(libs.arrow.fx.coroutines)
    implementation(libs.arrow.fx.stm)
    //endregion

    implementation(libs.janino)
}
