package io.pressurediary.server.backend.modules

import io.pressurediary.server.backend.authorization.AuthRouteUtils
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val commonModule = module {
    singleOf(::AuthRouteUtils)
}