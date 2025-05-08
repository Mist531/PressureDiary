package io.pressurediary.server.backend.configure

import io.ktor.server.application.Application
import io.ktor.server.application.install
import org.koin.core.logger.Level
import org.koin.core.module.Module
import org.koin.ktor.plugin.Koin
import org.koin.logger.slf4jLogger

fun Application.configureKoin(modules: List<Module>) {
    install(Koin) {
        slf4jLogger(Level.DEBUG)
        modules(modules)
    }
}