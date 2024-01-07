package com.backend.database.tables

import org.jetbrains.exposed.sql.Table

object PressureRecordTagLinksTable : Table("PressureRecordTagLinksTable") {
    val pressureRecord = reference("pressureRecordUUID", PressureRecordsTable)
    val tag = reference("tagUUID", TagsTable)
    override val primaryKey =
        PrimaryKey(pressureRecord, tag, name = "PK_PressureRecord_TagLink") // Композитный первичный ключ
}
