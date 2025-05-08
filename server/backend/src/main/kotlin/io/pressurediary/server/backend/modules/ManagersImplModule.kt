package io.pressurediary.server.backend.modules

import io.pressurediary.server.backend.managersImpl.DeviceManager
import io.pressurediary.server.backend.managersImpl.DeviceManagerImpl
import io.pressurediary.server.backend.managersImpl.HistoryManager
import io.pressurediary.server.backend.managersImpl.HistoryManagerImpl
import io.pressurediary.server.backend.managersImpl.NotificationsManager
import io.pressurediary.server.backend.managersImpl.NotificationsManagerImpl
import io.pressurediary.server.backend.managersImpl.PressureRecordManager
import io.pressurediary.server.backend.managersImpl.PressureRecordManagerImpl
import io.pressurediary.server.backend.managersImpl.PressureRecordTagLinksManager
import io.pressurediary.server.backend.managersImpl.PressureRecordTagLinksManagerImpl
import io.pressurediary.server.backend.managersImpl.TagsManager
import io.pressurediary.server.backend.managersImpl.TagsManagerImpl
import io.pressurediary.server.backend.managersImpl.UserManager
import io.pressurediary.server.backend.managersImpl.UserManagerImpl
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val managersImplModule = module {
    singleOf<UserManager>(::UserManagerImpl)
    singleOf<PressureRecordManager>(::PressureRecordManagerImpl)
    singleOf<DeviceManager>(::DeviceManagerImpl)
    singleOf<TagsManager>(::TagsManagerImpl)
    singleOf<PressureRecordTagLinksManager>(::PressureRecordTagLinksManagerImpl)
    singleOf<NotificationsManager>(::NotificationsManagerImpl)
    singleOf<HistoryManager>(::HistoryManagerImpl)
}
