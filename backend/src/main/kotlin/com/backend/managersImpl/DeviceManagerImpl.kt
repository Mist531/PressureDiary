package com.backend.managersImpl

import com.backend.managers.deviceManager.DeleteUserDeviceManager
import com.backend.managers.deviceManager.GetUserDevicesListManager
import com.backend.managers.deviceManager.PostDeviceForUserManager
import com.backend.models.DeleteUserDeviceModel
import com.backend.models.DeviceModel
import com.backend.models.PostDeviceForUserModel
import io.ktor.http.*
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.util.*

interface DeviceManager {
    suspend fun getUserDevicesList(id: UUID): List<DeviceModel>
    suspend fun addDeviceForUser(model: PostDeviceForUserModel): HttpStatusCode
    suspend fun deleteUserDevice(model: DeleteUserDeviceModel): HttpStatusCode
}


class DeviceManagerImpl : DeviceManager, KoinComponent {

    override suspend fun getUserDevicesList(id: UUID): List<DeviceModel> {
        val manager: GetUserDevicesListManager by inject()
        return runCatching {
            manager.invoke(id, Unit)
        }.getOrElse {
            it.printStackTrace()
            throw it
        }
    }

    override suspend fun addDeviceForUser(model: PostDeviceForUserModel): HttpStatusCode {
        val manager: PostDeviceForUserManager by inject()
        return runCatching {
            manager.invoke(Unit, model)
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
