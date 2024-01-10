package com.backend.managersImpl

import com.backend.managers.tagsManager.DeleteAllTagsForUserManager
import com.backend.managers.tagsManager.DeleteUserTagManager
import com.backend.managers.tagsManager.GetUserTagsListManager
import com.backend.managers.tagsManager.PostTagForUserManager
import com.example.api.models.AddTagModel
import com.example.api.models.DeleteUserTagModel
import com.example.api.models.TagModel
import io.ktor.http.*
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.util.*

interface TagsManager {
    suspend fun getUserTagsList(userUUID: UUID): List<TagModel>
    suspend fun addTagForUser(userUUID: UUID, addTagModel: AddTagModel): HttpStatusCode
    suspend fun deleteUserTag(deleteUserTagModel: DeleteUserTagModel): HttpStatusCode
    suspend fun deleteAllTagsForUser(userUUID: UUID): HttpStatusCode
}

class TagsManagerImpl : TagsManager, KoinComponent {
    override suspend fun getUserTagsList(userUUID: UUID): List<TagModel> {
        val manager: GetUserTagsListManager by inject()
        return runCatching {
            manager.invoke(userUUID, Unit)
        }.getOrElse {
            it.printStackTrace()
            throw it
        }
    }

    override suspend fun addTagForUser(userUUID: UUID, addTagModel: AddTagModel): HttpStatusCode {
        val manager: PostTagForUserManager by inject()
        return runCatching {
            manager.invoke(userUUID, addTagModel)
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
            manager.invoke(Unit, deleteUserTagModel)
        }.getOrElse {
            it.printStackTrace()
            throw it
        }.let {
            HttpStatusCode.OK
        }
    }

    override suspend fun deleteAllTagsForUser(userUUID: UUID): HttpStatusCode {
        val manager: DeleteAllTagsForUserManager by inject()
        return runCatching {
            manager.invoke(userUUID, Unit)
        }.getOrElse {
            it.printStackTrace()
            throw it
        }.let {
            HttpStatusCode.OK
        }
    }
}