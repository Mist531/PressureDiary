package io.pressurediary.android.common.modules

import io.pressurediary.android.common.data.repository.DeviceRepository
import io.pressurediary.android.common.data.repository.DeviceRepositoryImpl
import io.pressurediary.android.common.data.repository.HistoryRepository
import io.pressurediary.android.common.data.repository.HistoryRepositoryImpl
import io.pressurediary.android.common.data.repository.NotificationsRepository
import io.pressurediary.android.common.data.repository.NotificationsRepositoryImpl
import io.pressurediary.android.common.data.repository.PressureRecordRepository
import io.pressurediary.android.common.data.repository.PressureRecordRepositoryImpl
import io.pressurediary.android.common.data.repository.PressureRecordTagLinksRepository
import io.pressurediary.android.common.data.repository.PressureRecordTagLinksRepositoryImpl
import io.pressurediary.android.common.data.repository.TagsRepository
import io.pressurediary.android.common.data.repository.TagsRepositoryImpl
import io.pressurediary.android.common.data.repository.UserRepository
import io.pressurediary.android.common.data.repository.UserRepositoryImpl
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