package com.backend.modules

import com.backend.managersImpl.*
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val managersImplModule = module {
    singleOf<UserManager>(::UserManagerImpl)
    singleOf<PressureRecordManager>(::PressureRecordManagerImpl)
    singleOf<DeviceManager>(::DeviceManagerImpl)
    singleOf<TagsManager>(::TagsManagerImpl)
    singleOf<PressureRecordTagLinksManager>(::PressureRecordTagLinksManagerImpl)
}
