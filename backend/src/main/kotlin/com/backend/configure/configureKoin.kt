package com.backend.configure

import io.ktor.server.application.*
import org.koin.core.logger.Level
import org.koin.core.module.Module
import org.koin.ktor.plugin.Koin
import org.koin.ktor.plugin.KoinApplicationStarted
import org.koin.ktor.plugin.KoinApplicationStopPreparing
import org.koin.ktor.plugin.KoinApplicationStopped
import org.koin.logger.slf4jLogger

fun Application.configureKoin(modules: List<Module>) {
    install(Koin) {
        environment.monitor.subscribe(KoinApplicationStarted) {
            log.info("DFDF:Koin started.")
        }

        environment.monitor.subscribe(KoinApplicationStopPreparing) {
            log.info("DFDF:Koin stopping...")
        }

        environment.monitor.subscribe(KoinApplicationStopped) {
            log.info("DFDF:Koin stopped.")
        }
        slf4jLogger(Level.DEBUG)
        modules(modules)
    }
}