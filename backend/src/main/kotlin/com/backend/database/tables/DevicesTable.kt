package com.backend.database.tables

import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.javatime.date
import java.util.*

object DevicesTable : UUIDTable("Devices", "deviceUUID") {
    val userUUID = reference("userUUID", UsersTable)
    val deviceType = enumeration("deviceType", DeviceType::class)
    val lastSyncDate = date("lastSyncDate")
}

class Device(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<Device>(DevicesTable)

    var userUUID by User referencedOn DevicesTable.userUUID
    var deviceType by DevicesTable.deviceType
    var lastSyncDate by DevicesTable.lastSyncDate
}
