package com.backend.database.tables

import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.javatime.datetime
import java.util.*

object HistoryTable : UUIDTable("History", "historyUUID") {
    val pressureRecordUUID = reference("pressureRecordUUID", PressureRecordsTable)
    val dateTimeModified = datetime("dateTimeModified")
    val previousValue = text("previousValue") // Хранение в формате JSON
}

class History(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<History>(HistoryTable)

    var pressureRecordUUID by PressureRecord referencedOn HistoryTable.pressureRecordUUID
    var dateTimeModified by HistoryTable.dateTimeModified
    var previousValue by HistoryTable.previousValue
}
