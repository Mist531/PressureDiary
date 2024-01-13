package com.backend.database.tables

import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.UUIDTable
import java.util.*

object TagsTable : UUIDTable("Tags", "tagUUID") {
    val userUUID = reference("userUUID", UsersTable)
    val tagName = varchar("name", 255)
}

class Tag(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<Tag>(TagsTable)

    var userUUID by User referencedOn TagsTable.userUUID
    var name by TagsTable.tagName
}
