package io.pressurediary.server.backend.managers.deviceManager.impl

import io.pressurediary.server.api.models.DeviceModel
import io.pressurediary.server.backend.database.tables.DevicesTable
import io.pressurediary.server.backend.managers.deviceManager.GetUserDevicesListManager
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.selectAll
import java.util.UUID

class GetUserDevicesListManagerImpl : GetUserDevicesListManager {
    override suspend fun request(
        param: UUID,
        request: Unit
    ): List<DeviceModel> = requestTransaction {
        DevicesTable.selectAll().where {
            DevicesTable.userUUID eq param
        }.map(::toDevice)
    }

    private fun toDevice(
        row: ResultRow
    ): DeviceModel = DeviceModel(
        deviceUUID = row[DevicesTable.id].value,
        deviceType = row[DevicesTable.deviceType],
        lastSyncDate = row[DevicesTable.lastSyncDate]
    )
}