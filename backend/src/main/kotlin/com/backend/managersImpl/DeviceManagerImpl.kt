package com.backend.managersImpl

import com.backend.managers.deviceManager.DeleteUserDeviceManager
import com.backend.managers.deviceManager.GetUserDevicesListManager
import com.backend.managers.deviceManager.PostDeviceForUserManager
import com.example.api.models.DeleteUserDeviceModel
import com.example.api.models.DeviceModel
import com.example.api.models.PostDeviceForUserModel
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
            manager.invoke(userId, Unit)
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
            manager.invoke(userId, model)
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
            manager.invoke(Unit, model)
        }.getOrElse {
            it.printStackTrace()
            throw it
        }.let {
            HttpStatusCode.OK
        }
    }
}
