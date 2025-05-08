package io.pressurediary.server.backend.managers.tagsManager.impl

import io.pressurediary.server.api.models.TagModel
import io.pressurediary.server.backend.database.tables.TagsTable
import io.pressurediary.server.backend.managers.tagsManager.GetUserTagsListManager
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import java.util.UUID

class GetUserTagsListManagerImpl : GetUserTagsListManager {
    override suspend fun request(
        param: UUID,
        request: Unit
    ): List<TagModel> = requestTransaction {
        TagsTable.selectAll().where {
            TagsTable.userUUID eq param
        }.map(::toTagModel)
    }

    private fun toTagModel(
        row: ResultRow
    ): TagModel = TagModel(
        tagId = row[TagsTable.id].value,
        name = row[TagsTable.tagName]
    )
}