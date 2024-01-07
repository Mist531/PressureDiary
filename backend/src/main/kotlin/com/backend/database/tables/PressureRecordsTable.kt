package com.backend.database.tables

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.javatime.datetime
import java.util.*

object PressureRecordsTable : UUIDTable("PressureRecords", "pressureRecordUUID") {
    val userUUID = reference("userUUID", UsersTable)
    val dateTimeRecord = datetime("dateTimeRecord")
    val systolic = integer("systolic")
    val diastolic = integer("diastolic")
    val pulse = integer("pulse")
    val note = varchar("note", 255)
    val deviceType = enumerationByName("deviceType", 10, DeviceType::class)
}

class PressureRecord(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<PressureRecord>(PressureRecordsTable)

    var userUUID by User referencedOn PressureRecordsTable.userUUID
    var dateTimeRecord by PressureRecordsTable.dateTimeRecord
    var systolic by PressureRecordsTable.systolic
    var diastolic by PressureRecordsTable.diastolic
    var pulse by PressureRecordsTable.pulse
    var note by PressureRecordsTable.note
    var deviceType by PressureRecordsTable.deviceType
}

@Serializable
enum class DeviceType {
    ANDROID_WEAR,
    ANDROID,
    IOS
}
