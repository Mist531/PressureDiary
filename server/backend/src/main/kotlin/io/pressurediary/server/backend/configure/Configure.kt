package io.pressurediary.server.backend.configure

import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.resources.Resources
import org.koin.core.module.Module

fun Application.configure(
    listModules: List<Module>
) {
    install(Resources)
    configureKoin(
        listModules
    )
    configureLogging()
    configureHTTP()
    configureAuthentication()
    configureRouting()
    configureSerialization()
}