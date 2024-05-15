package com.mist.common.modules

import com.mist.common.data.repository.DeviceRepository
import com.mist.common.data.repository.DeviceRepositoryImpl
import com.mist.common.data.repository.HistoryRepository
import com.mist.common.data.repository.HistoryRepositoryImpl
import com.mist.common.data.repository.NotificationsRepository
import com.mist.common.data.repository.NotificationsRepositoryImpl
import com.mist.common.data.repository.PressureRecordRepository
import com.mist.common.data.repository.PressureRecordRepositoryImpl
import com.mist.common.data.repository.PressureRecordTagLinksRepository
import com.mist.common.data.repository.PressureRecordTagLinksRepositoryImpl
import com.mist.common.data.repository.TagsRepository
import com.mist.common.data.repository.TagsRepositoryImpl
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