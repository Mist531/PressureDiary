package com.backend.managers.tagsManager.impl

import com.backend.database.tables.TagsTable
import com.backend.managers.tagsManager.GetUserTagsListManager
import com.example.api.models.TagModel
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import java.util.UUID

class GetUserTagsListManagerImpl : GetUserTagsListManager {
    override suspend operator fun invoke(param: UUID, request: Unit): List<TagModel> =
        newSuspendedTransaction(Dispatchers.IO) {
            TagsTable.selectAll().where { TagsTable.userUUID eq param }.map(::toTagModel)
        }

    private fun toTagModel(row: ResultRow): TagModel =
        TagModel(
            tagId = row[TagsTable.id].value,
            name = row[TagsTable.tagName]
        )
}