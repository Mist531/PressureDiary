package io.pressurediary.server.backend.managersImpl

import io.pressurediary.server.backend.managers.deviceManager.DeleteUserDeviceManager
import io.pressurediary.server.backend.managers.deviceManager.GetUserDevicesListManager
import io.pressurediary.server.backend.managers.deviceManager.PostDeviceForUserManager
import io.pressurediary.server.api.models.DeleteUserDeviceModel
import io.pressurediary.server.api.models.DeviceModel
import io.pressurediary.server.api.models.PostDeviceForUserModel
import io.ktor.http.HttpStatusCode
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.util.UUID

interface DeviceManager {
    suspend fun getUserDevicesList(userId: UUID): List<DeviceModel>
    suspend fun addDeviceForUser(
        userId: UUID,
        model: PostDeviceForUserModel
    ): HttpStatusCode

    suspend fun deleteUserDevice(model: DeleteUserDeviceModel): HttpStatusCode
}


class DeviceManagerImpl : DeviceManager, KoinComponent {

    override suspend fun getUserDevicesList(
        userId: UUID
    ): List<DeviceModel> {
        val manager: GetUserDevicesListManager by inject()
        return runCatching {
            manager.request(userId, Unit)
        }.getOrElse {
            it.printStackTrace()
            throw it
        }
    }

    override suspend fun addDeviceForUser(
        userId: UUID,
        model: PostDeviceForUserModel
    ): HttpStatusCode {
        val manager: PostDeviceForUserManager by inject()
        return runCatching {
            manager.request(userId, model)
        }.getOrElse {
            it.printStackTrace()
            throw it
        }.let {
            HttpStatusCode.Created
        }
    }

    override suspend fun deleteUserDevice(model: DeleteUserDeviceModel): HttpStatusCode {
        val manager: DeleteUserDeviceManager by inject()
        return runCatching {
            manager.request(Unit, model)
        }.getOrElse {
            it.printStackTrace()
            throw it
        }.let {
            HttpStatusCode.OK
        }
    }
}
