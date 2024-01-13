package com.backend.managers.tagsManager.impl

import com.backend.database.tables.TagsTable
import com.backend.managers.tagsManager.DeleteAllTagsForUserManager
import io.ktor.http.*
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import java.util.*

class DeleteAllTagsForUserManagerImpl : DeleteAllTagsForUserManager {
    override suspend operator fun invoke(param: UUID, request: Unit): HttpStatusCode =
        newSuspendedTransaction(Dispatchers.IO) {
            TagsTable.deleteWhere { userUUID eq param }
            HttpStatusCode.OK
        }
}