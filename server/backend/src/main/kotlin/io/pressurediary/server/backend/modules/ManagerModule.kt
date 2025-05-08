package io.pressurediary.server.backend.modules

import io.pressurediary.server.backend.managers.deviceManager.DeleteUserDeviceManager
import io.pressurediary.server.backend.managers.deviceManager.GetUserDevicesListManager
import io.pressurediary.server.backend.managers.deviceManager.PostDeviceForUserManager
import io.pressurediary.server.backend.managers.deviceManager.impl.DeleteUserDeviceManagerImpl
import io.pressurediary.server.backend.managers.deviceManager.impl.GetUserDevicesListManagerImpl
import io.pressurediary.server.backend.managers.deviceManager.impl.PostDeviceForUserManagerImpl
import io.pressurediary.server.backend.managers.historyManager.GetHistoryForRecordManager
import io.pressurediary.server.backend.managers.historyManager.RestoreRecordFromHistoryManager
import io.pressurediary.server.backend.managers.historyManager.impl.GetHistoryForRecordManagerImpl
import io.pressurediary.server.backend.managers.historyManager.impl.RestoreRecordFromHistoryManagerImpl
import io.pressurediary.server.backend.managers.notificationsManager.GetAllNotificationsManager
import io.pressurediary.server.backend.managers.notificationsManager.GetNextNotificationManager
import io.pressurediary.server.backend.managers.notificationsManager.UpdateNotificationManager
import io.pressurediary.server.backend.managers.notificationsManager.impl.GetAllNotificationsManagerImpl
import io.pressurediary.server.backend.managers.notificationsManager.impl.GetNextNotificationManagerImpl
import io.pressurediary.server.backend.managers.notificationsManager.impl.UpdateNotificationManagerImpl
import io.pressurediary.server.backend.managers.pressureRecordTagLinksManager.AddPressureRecordTagLinkManager
import io.pressurediary.server.backend.managers.pressureRecordTagLinksManager.DeletePressureRecordTagLinkByRecordManager
import io.pressurediary.server.backend.managers.pressureRecordTagLinksManager.DeletePressureRecordTagLinkByTagManager
import io.pressurediary.server.backend.managers.pressureRecordTagLinksManager.impl.AddPressureRecordTagLinkManagerImpl
import io.pressurediary.server.backend.managers.pressureRecordTagLinksManager.impl.DeletePressureRecordTagLinkByRecordManagerImpl
import io.pressurediary.server.backend.managers.pressureRecordTagLinksManager.impl.DeletePressureRecordTagLinkByTagManagerImpl
import io.pressurediary.server.backend.managers.pressureRecordsManager.DeletePressureRecordManager
import io.pressurediary.server.backend.managers.pressureRecordsManager.GetAllPressureRecordsManager
import io.pressurediary.server.backend.managers.pressureRecordsManager.GetPaginatedPressureRecordsManager
import io.pressurediary.server.backend.managers.pressureRecordsManager.PostPressureRecordManager
import io.pressurediary.server.backend.managers.pressureRecordsManager.PutPressureRecordManager
import io.pressurediary.server.backend.managers.pressureRecordsManager.impl.DeletePressureRecordManagerImpl
import io.pressurediary.server.backend.managers.pressureRecordsManager.impl.GetAllPressureRecordsManagerImpl
import io.pressurediary.server.backend.managers.pressureRecordsManager.impl.GetPaginatedPressureRecordsManagerImpl
import io.pressurediary.server.backend.managers.pressureRecordsManager.impl.PostPressureRecordManagerImpl
import io.pressurediary.server.backend.managers.pressureRecordsManager.impl.PutPressureRecordManagerImpl
import io.pressurediary.server.backend.managers.tagsManager.DeleteAllTagsForUserManager
import io.pressurediary.server.backend.managers.tagsManager.DeleteUserTagManager
import io.pressurediary.server.backend.managers.tagsManager.GetUserTagsListManager
import io.pressurediary.server.backend.managers.tagsManager.PostTagForUserManager
import io.pressurediary.server.backend.managers.tagsManager.impl.DeleteAllTagsForUserManagerImpl
import io.pressurediary.server.backend.managers.tagsManager.impl.DeleteUserTagManagerImpl
import io.pressurediary.server.backend.managers.tagsManager.impl.GetUserTagsListManagerImpl
import io.pressurediary.server.backend.managers.tagsManager.impl.PostTagForUserManagerImpl
import io.pressurediary.server.backend.managers.usersManager.DeleteUserManager
import io.pressurediary.server.backend.managers.usersManager.LoginUserManager
import io.pressurediary.server.backend.managers.usersManager.PostUserManager
import io.pressurediary.server.backend.managers.usersManager.PutUserManager
import io.pressurediary.server.backend.managers.usersManager.RefreshTokensManager
import io.pressurediary.server.backend.managers.usersManager.impl.DeleteUserManagerImpl
import io.pressurediary.server.backend.managers.usersManager.impl.LoginUserManagerImpl
import io.pressurediary.server.backend.managers.usersManager.impl.PostUserManagerImpl
import io.pressurediary.server.backend.managers.usersManager.impl.PutUserManagerImpl
import io.pressurediary.server.backend.managers.usersManager.impl.RefreshTokensManagerImpl
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
    singleOf(::RefreshTokensManagerImpl) {
        bind<RefreshTokensManager>()
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
    singleOf(::GetAllPressureRecordsManagerImpl) {
        bind<GetAllPressureRecordsManager>()
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

    //region Notifications
    singleOf(::GetAllNotificationsManagerImpl) {
        bind<GetAllNotificationsManager>()
    }
    singleOf(::GetNextNotificationManagerImpl) {
        bind<GetNextNotificationManager>()
    }
    singleOf(::UpdateNotificationManagerImpl) {
        bind<UpdateNotificationManager>()
    }
    //endregion

    //region History
    singleOf(::RestoreRecordFromHistoryManagerImpl) {
        bind<RestoreRecordFromHistoryManager>()
    }
    singleOf(::GetHistoryForRecordManagerImpl) {
        bind<GetHistoryForRecordManager>()
    }
    //endregion
}