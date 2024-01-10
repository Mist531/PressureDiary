package com.backend.managers.tagsManager

import com.example.api.models.AddTagModel
import com.example.api.models.DeleteUserTagModel
import com.example.api.models.TagModel
import com.example.managers.SimpleManager
import io.ktor.http.*
import java.util.*

interface GetUserTagsListManager : TagsManager<UUID, Unit, List<TagModel>>
interface PostTagForUserManager : TagsManager<UUID, AddTagModel, HttpStatusCode>
interface DeleteUserTagManager : TagsManager<Unit, DeleteUserTagModel, HttpStatusCode>
interface DeleteAllTagsForUserManager : TagsManager<UUID, Unit, HttpStatusCode>

interface TagsManager<Param, Request, Response> : SimpleManager<Param, Request, Response>
