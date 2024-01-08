package com.backend.managers.tagsManager.impl

import com.backend.database.tables.TagsTable
import com.backend.managers.tagsManager.PostTagForUserManager
import com.backend.models.AddTagModel
import io.ktor.http.*
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import java.util.*

class PostTagForUserManagerImpl : PostTagForUserManager {
    override suspend operator fun invoke(param: UUID, request: AddTagModel): HttpStatusCode =
        newSuspendedTransaction(Dispatchers.IO) {
            TagsTable.insert {
                it[userUUID] = param
                it[name] = request.name
            }
            HttpStatusCode.Created
        }
}