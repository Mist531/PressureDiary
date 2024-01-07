package com.backend.modules

import com.backend.authorization.AuthRouteUtils
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val commonModule = module {
    singleOf(::AuthRouteUtils)
}