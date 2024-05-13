package com.backend.managers.pressureRecordTagLinksManager.impl

import com.backend.database.tables.PressureRecordTagLinksTable
import com.backend.managers.pressureRecordTagLinksManager.AddPressureRecordTagLinkManager
import com.example.api.models.AddPressureRecordTagLinkModel
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

class AddPressureRecordTagLinkManagerImpl : AddPressureRecordTagLinkManager {
    override suspend operator fun invoke(param: Unit, request: AddPressureRecordTagLinkModel): HttpStatusCode =
        newSuspendedTransaction(Dispatchers.IO) {
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
