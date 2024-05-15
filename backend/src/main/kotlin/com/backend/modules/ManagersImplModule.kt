package com.backend.modules

import com.backend.managersImpl.DeviceManager
import com.backend.managersImpl.DeviceManagerImpl
import com.backend.managersImpl.HistoryManager
import com.backend.managersImpl.HistoryManagerImpl
import com.backend.managersImpl.NotificationsManager
import com.backend.managersImpl.NotificationsManagerImpl
import com.backend.managersImpl.PressureRecordManager
import com.backend.managersImpl.PressureRecordManagerImpl
import com.backend.managersImpl.PressureRecordTagLinksManager
import com.backend.managersImpl.PressureRecordTagLinksManagerImpl
import com.backend.managersImpl.TagsManager
import com.backend.managersImpl.TagsManagerImpl
import com.backend.managersImpl.UserManager
import com.backend.managersImpl.UserManagerImpl
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
