package io.pressurediary.server.backend.managers.pressureRecordTagLinksManager.impl

import io.ktor.http.HttpStatusCode
import io.pressurediary.server.api.models.AddPressureRecordTagLinkModel
import io.pressurediary.server.backend.database.tables.PressureRecordTagLinksTable
import io.pressurediary.server.backend.managers.pressureRecordTagLinksManager.AddPressureRecordTagLinkManager
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

class AddPressureRecordTagLinkManagerImpl : AddPressureRecordTagLinkManager {
    override suspend fun request(
        param: Unit,
        request: AddPressureRecordTagLinkModel
    ): HttpStatusCode = requestTransaction {
        val exists = PressureRecordTagLinksTable.selectAll().where {
            (PressureRecordTagLinksTable.pressureRecordUUID eq request.pressureRecordUUID) and
                    (PressureRecordTagLinksTable.tagUUID eq request.tagUUID)
        }.singleOrNull() != null

        if (!exists) {
            PressureRecordTagLinksTable.insert {
                it[pressureRecordUUID] = request.pressureRecordUUID
                it[tagUUID] = request.tagUUID
            }
            HttpStatusCode.Created
        } else {
            throw Exception("Связь уже существует")
        }
    }
}
