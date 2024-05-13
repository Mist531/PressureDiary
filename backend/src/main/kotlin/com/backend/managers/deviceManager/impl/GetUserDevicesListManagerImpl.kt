package com.backend.managers.deviceManager.impl

import com.backend.database.tables.DevicesTable
import com.backend.managers.deviceManager.GetUserDevicesListManager
import com.example.api.models.DeviceModel
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import java.util.UUID

class GetUserDevicesListManagerImpl : GetUserDevicesListManager {
    override suspend operator fun invoke(param: UUID, request: Unit): List<DeviceModel> =
        newSuspendedTransaction(Dispatchers.IO) {
            DevicesTable.selectAll().where { DevicesTable.userUUID eq param }.map(::toDevice)
        }

    private fun toDevice(row: ResultRow): DeviceModel =
        DeviceModel(
            deviceUUID = row[DevicesTable.id].value,
            deviceType = row[DevicesTable.deviceType],
            lastSyncDate = row[DevicesTable.lastSyncDate]
        )
}