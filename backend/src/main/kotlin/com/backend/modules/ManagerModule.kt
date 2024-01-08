package com.backend.modules

import com.backend.managers.deviceManager.DeleteUserDeviceManager
import com.backend.managers.deviceManager.GetUserDevicesListManager
import com.backend.managers.deviceManager.PostDeviceForUserManager
import com.backend.managers.deviceManager.impl.DeleteUserDeviceManagerImpl
import com.backend.managers.deviceManager.impl.GetUserDevicesListManagerImpl
import com.backend.managers.deviceManager.impl.PostDeviceForUserManagerImpl
import com.backend.managers.pressureRecordTagLinksManager.AddPressureRecordTagLinkManager
import com.backend.managers.pressureRecordTagLinksManager.DeletePressureRecordTagLinkByRecordManager
import com.backend.managers.pressureRecordTagLinksManager.DeletePressureRecordTagLinkByTagManager
import com.backend.managers.pressureRecordTagLinksManager.impl.AddPressureRecordTagLinkManagerImpl
import com.backend.managers.pressureRecordTagLinksManager.impl.DeletePressureRecordTagLinkByRecordManagerImpl
import com.backend.managers.pressureRecordTagLinksManager.impl.DeletePressureRecordTagLinkByTagManagerImpl
import com.backend.managers.pressureRecordsManager.DeletePressureRecordManager
import com.backend.managers.pressureRecordsManager.GetPaginatedPressureRecordsManager
import com.backend.managers.pressureRecordsManager.PostPressureRecordManager
import com.backend.managers.pressureRecordsManager.PutPressureRecordManager
import com.backend.managers.pressureRecordsManager.impl.DeletePressureRecordManagerImpl
import com.backend.managers.pressureRecordsManager.impl.GetPaginatedPressureRecordsManagerImpl
import com.backend.managers.pressureRecordsManager.impl.PostPressureRecordManagerImpl
import com.backend.managers.pressureRecordsManager.impl.PutPressureRecordManagerImpl
import com.backend.managers.tagsManager.DeleteAllTagsForUserManager
import com.backend.managers.tagsManager.DeleteUserTagManager
import com.backend.managers.tagsManager.GetUserTagsListManager
import com.backend.managers.tagsManager.PostTagForUserManager
import com.backend.managers.tagsManager.impl.DeleteAllTagsForUserManagerImpl
import com.backend.managers.tagsManager.impl.DeleteUserTagManagerImpl
import com.backend.managers.tagsManager.impl.GetUserTagsListManagerImpl
import com.backend.managers.tagsManager.impl.PostTagForUserManagerImpl
import com.backend.managers.usersManager.DeleteUserManager
import com.backend.managers.usersManager.LoginUserManager
import com.backend.managers.usersManager.PostUserManager
import com.backend.managers.usersManager.PutUserManager
import com.backend.managers.usersManager.impl.DeleteUserManagerImpl
import com.backend.managers.usersManager.impl.LoginUserManagerImpl
import com.backend.managers.usersManager.impl.PostUserManagerImpl
import com.backend.managers.usersManager.impl.PutUserManagerImpl
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val managerModule = module {

    //region Users
    singleOf(::DeleteUserManagerImpl) {
        bind<DeleteUserManager>()
    }
    singleOf(::LoginUserManagerImpl) {
        bind<LoginUserManager>()
    }
    singleOf(::PostUserManagerImpl) {
        bind<PostUserManager>()
    }
    singleOf(::PutUserManagerImpl) {
        bind<PutUserManager>()
    }
    //endregion

    //region PressureRecords
    singleOf(::DeletePressureRecordManagerImpl) {
        bind<DeletePressureRecordManager>()
    }
    singleOf(::GetPaginatedPressureRecordsManagerImpl) {
        bind<GetPaginatedPressureRecordsManager>()
    }
    singleOf(::PostPressureRecordManagerImpl) {
        bind<PostPressureRecordManager>()
    }
    singleOf(::PutPressureRecordManagerImpl) {
        bind<PutPressureRecordManager>()
    }
    //endregion

    //region Devices
    singleOf(::DeleteUserDeviceManagerImpl) {
        bind<DeleteUserDeviceManager>()
    }
    singleOf(::GetUserDevicesListManagerImpl) {
        bind<GetUserDevicesListManager>()
    }
    singleOf(::PostDeviceForUserManagerImpl) {
        bind<PostDeviceForUserManager>()
    }
    //endregion

    //region Tags
    singleOf(::GetUserTagsListManagerImpl) {
        bind<GetUserTagsListManager>()
    }
    singleOf(::PostTagForUserManagerImpl) {
        bind<PostTagForUserManager>()
    }
    singleOf(::DeleteUserTagManagerImpl) {
        bind<DeleteUserTagManager>()
    }
    singleOf(::DeleteAllTagsForUserManagerImpl) {
        bind<DeleteAllTagsForUserManager>()
    }
    //endregion

    //region PressureRecordsTagLink
    singleOf(::AddPressureRecordTagLinkManagerImpl) {
        bind<AddPressureRecordTagLinkManager>()
    }
    singleOf(::DeletePressureRecordTagLinkByRecordManagerImpl) {
        bind<DeletePressureRecordTagLinkByRecordManager>()
    }
    singleOf(::DeletePressureRecordTagLinkByTagManagerImpl) {
        bind<DeletePressureRecordTagLinkByTagManager>()
    }
    //endregion
}