package io.pressurediary.server.backend.database.tables

import org.jetbrains.exposed.sql.Table

object PressureRecordTagLinksTable : Table("PressureRecordTagLinksTable") {
    val pressureRecordUUID = reference("pressureRecordUUID", PressureRecordsTable)
    val tagUUID = reference("tagUUID", TagsTable)
    override val primaryKey = PrimaryKey(
        pressureRecordUUID, tagUUID, name = "PK_PressureRecord_TagLink"
    )
}
