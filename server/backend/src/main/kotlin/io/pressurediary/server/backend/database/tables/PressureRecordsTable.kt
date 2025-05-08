package io.pressurediary.server.backend.database.tables

import io.pressurediary.server.api.models.DeviceType
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.javatime.CurrentDateTime
import org.jetbrains.exposed.sql.javatime.datetime
import java.util.UUID

object PressureRecordsTable : UUIDTable("PressureRecords", "pressureRecordUUID") {
    val userUUID = reference("userUUID", UsersTable)
    val dateTimeRecord = datetime("dateTimeRecord").defaultExpression(CurrentDateTime)
    val systolic = integer("systolic")
    val diastolic = integer("diastolic")
    val pulse = integer("pulse")
    val note = varchar("note", 255)
    val deviceType = enumeration("deviceType", DeviceType::class)
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