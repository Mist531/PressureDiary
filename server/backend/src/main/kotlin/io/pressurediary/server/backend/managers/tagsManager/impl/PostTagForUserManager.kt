package io.pressurediary.server.backend.managers.tagsManager.impl

import io.ktor.http.HttpStatusCode
import io.pressurediary.server.api.models.AddTagModel
import io.pressurediary.server.backend.database.tables.TagsTable
import io.pressurediary.server.backend.managers.tagsManager.PostTagForUserManager
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import java.util.UUID

class PostTagForUserManagerImpl : PostTagForUserManager {
    override suspend fun request(
        param: UUID,
        request: AddTagModel
    ): HttpStatusCode = requestTransaction {
        TagsTable.insert {
            it[userUUID] = param
            it[tagName] = request.name
        }
        HttpStatusCode.Created
    }
}