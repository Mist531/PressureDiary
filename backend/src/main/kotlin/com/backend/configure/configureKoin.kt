package com.backend.configure

import io.ktor.server.application.*
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