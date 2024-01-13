package com.backend.managers.tagsManager.impl

import com.backend.database.tables.TagsTable
import com.backend.managers.tagsManager.DeleteUserTagManager
import com.example.api.models.DeleteUserTagModel
import io.ktor.http.*
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

class DeleteUserTagManagerImpl : DeleteUserTagManager {
    override suspend operator fun invoke(param: Unit, request: DeleteUserTagModel): HttpStatusCode =
        newSuspendedTransaction(Dispatchers.IO) {
            TagsTable.deleteWhere {
                id eq request.tagId
            }
            HttpStatusCode.OK
        }
}