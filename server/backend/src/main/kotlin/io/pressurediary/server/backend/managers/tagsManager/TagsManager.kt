package io.pressurediary.server.backend.managers.tagsManager

import io.pressurediary.server.backend.managers.SimpleManager
import io.pressurediary.server.api.models.AddTagModel
import io.pressurediary.server.api.models.DeleteUserTagModel
import io.pressurediary.server.api.models.TagModel
import io.ktor.http.HttpStatusCode
import java.util.UUID

interface GetUserTagsListManager : TagsManager<UUID, Unit, List<TagModel>>
interface PostTagForUserManager : TagsManager<UUID, AddTagModel, HttpStatusCode>
interface DeleteUserTagManager : TagsManager<Unit, DeleteUserTagModel, HttpStatusCode>
interface DeleteAllTagsForUserManager : TagsManager<UUID, Unit, HttpStatusCode>

interface TagsManager<Param, Request, Response> : SimpleManager<Param, Request, Response>
