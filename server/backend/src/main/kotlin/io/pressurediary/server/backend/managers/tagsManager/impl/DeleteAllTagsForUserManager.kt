package io.pressurediary.server.backend.managers.tagsManager.impl

import io.ktor.http.HttpStatusCode
import io.pressurediary.server.backend.database.tables.TagsTable
import io.pressurediary.server.backend.managers.tagsManager.DeleteAllTagsForUserManager
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import java.util.UUID

class DeleteAllTagsForUserManagerImpl : DeleteAllTagsForUserManager {
    override suspend fun request(
        param: UUID,
        request: Unit
    ): HttpStatusCode = requestTransaction {
        TagsTable.deleteWhere { userUUID eq param }
        HttpStatusCode.OK
    }
}