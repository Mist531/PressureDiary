package com.backend.database.tables

import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.javatime.date
import org.jetbrains.exposed.sql.javatime.time
import java.util.*

object NotificationsTable : UUIDTable("Notifications", "notificationUUID") {
    val userUUID = reference("userUUID", UsersTable)
    val message = varchar("message", 255)
    val timeToSend = time("timeToSend")
    val lastSentDate = date("lastSentDate").nullable()
}

class Notification(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<Notification>(NotificationsTable)

    var userUUID by User referencedOn NotificationsTable.userUUID
    var message by NotificationsTable.message
    var timeToSend by NotificationsTable.timeToSend
    var lastSentDate by NotificationsTable.lastSentDate
}
