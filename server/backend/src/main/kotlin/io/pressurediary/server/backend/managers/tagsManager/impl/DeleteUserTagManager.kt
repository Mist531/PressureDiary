package io.pressurediary.server.backend.managers.tagsManager.impl

import io.ktor.http.HttpStatusCode
import io.pressurediary.server.api.models.DeleteUserTagModel
import io.pressurediary.server.backend.database.tables.TagsTable
import io.pressurediary.server.backend.managers.tagsManager.DeleteUserTagManager
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

class DeleteUserTagManagerImpl : DeleteUserTagManager {
    override suspend fun request(
        param: Unit,
        request: DeleteUserTagModel
    ): HttpStatusCode = requestTransaction {
        TagsTable.deleteWhere {
            id eq request.tagId
        }
        HttpStatusCode.OK
    }
}