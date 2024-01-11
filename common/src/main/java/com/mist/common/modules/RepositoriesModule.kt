package com.mist.common.modules

import com.mist.common.data.repository.DeviceRepository
import com.mist.common.data.repository.DeviceRepositoryImpl
import com.mist.common.data.repository.UserRepository
import com.mist.common.data.repository.UserRepositoryImpl
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

internal val repositoriesModule = module {
    singleOf(::DeviceRepositoryImpl) {
        bind<DeviceRepository>()
    }
    singleOf(::UserRepositoryImpl) {
        bind<UserRepository>()
    }
}