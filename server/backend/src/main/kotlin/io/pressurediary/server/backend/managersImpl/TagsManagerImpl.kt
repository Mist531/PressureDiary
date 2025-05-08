package io.pressurediary.server.backend.managersImpl

import io.pressurediary.server.backend.managers.tagsManager.DeleteAllTagsForUserManager
import io.pressurediary.server.backend.managers.tagsManager.DeleteUserTagManager
import io.pressurediary.server.backend.managers.tagsManager.GetUserTagsListManager
import io.pressurediary.server.backend.managers.tagsManager.PostTagForUserManager
import io.pressurediary.server.api.models.AddTagModel
import io.pressurediary.server.api.models.DeleteUserTagModel
import io.pressurediary.server.api.models.TagModel
import io.ktor.http.HttpStatusCode
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.util.UUID

interface TagsManager {
    suspend fun getUserTagsList(userId: UUID): List<TagModel>
    suspend fun addTagForUser(userId: UUID, addTagModel: AddTagModel): HttpStatusCode
    suspend fun deleteUserTag(deleteUserTagModel: DeleteUserTagModel): HttpStatusCode
    suspend fun deleteAllTagsForUser(userId: UUID): HttpStatusCode
}

class TagsManagerImpl : TagsManager, KoinComponent {
    override suspend fun getUserTagsList(userId: UUID): List<TagModel> {
        val manager: GetUserTagsListManager by inject()
        return runCatching {
            manager.request(userId, Unit)
        }.getOrElse {
            it.printStackTrace()
            throw it
        }
    }

    override suspend fun addTagForUser(userId: UUID, addTagModel: AddTagModel): HttpStatusCode {
        val manager: PostTagForUserManager by inject()
        return runCatching {
            manager.request(userId, addTagModel)
        }.getOrElse {
            it.printStackTrace()
            throw it
        }.let {
            HttpStatusCode.OK
        }
    }

    override suspend fun deleteUserTag(deleteUserTagModel: DeleteUserTagModel): HttpStatusCode {
        val manager: DeleteUserTagManager by inject()
        return runCatching {
            manager.request(Unit, deleteUserTagModel)
        }.getOrElse {
            it.printStackTrace()
            throw it
        }.let {
            HttpStatusCode.OK
        }
    }

    override suspend fun deleteAllTagsForUser(userId: UUID): HttpStatusCode {
        val manager: DeleteAllTagsForUserManager by inject()
        return runCatching {
            manager.request(userId, Unit)
        }.getOrElse {
            it.printStackTrace()
            throw it
        }.let {
            HttpStatusCode.OK
        }
    }
}