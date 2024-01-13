package com.mist.common.modules

import com.mist.common.data.repository.*
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
    singleOf(::TagsRepositoryImpl) {
        bind<TagsRepository>()
    }
    singleOf(::PressureRecordRepositoryImpl) {
        bind<PressureRecordRepository>()
    }
    singleOf(::PressureRecordTagLinksRepositoryImpl) {
        bind<PressureRecordTagLinksRepository>()
    }
    singleOf(::NotificationsRepositoryImpl) {
        bind<NotificationsRepository>()
    }
    singleOf(::HistoryRepositoryImpl) {
        bind<HistoryRepository>()
    }
}