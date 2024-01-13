package com.backend.database.tables

import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.javatime.datetime
import java.util.*

//TODO: триггер что бы не было больше 5 записей в истории
object HistoryTable : UUIDTable("History", "historyUUID") {
    val pressureRecordUUID = reference("pressureRecordUUID", PressureRecordsTable)
    val dateTimeModified = datetime("dateTimeModified")
    val dateTimeRecord = datetime("dateTimeRecord")
    val systolic = integer("systolic")
    val diastolic = integer("diastolic")
    val pulse = integer("pulse")
    val note = varchar("note", 255)
}

class History(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<History>(HistoryTable)

    var pressureRecordUUID by PressureRecord referencedOn HistoryTable.pressureRecordUUID
    var dateTimeModified by HistoryTable.dateTimeModified
    var dateTimeRecord by HistoryTable.dateTimeRecord
    var systolic by HistoryTable.systolic
    var diastolic by HistoryTable.diastolic
    var pulse by HistoryTable.pulse
    var note by HistoryTable.note
}
