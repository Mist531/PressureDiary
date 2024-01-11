package com.backend.managers.usersManager

import com.example.api.models.LoginModel
import com.example.api.models.UserIdModel
import com.example.api.models.PostUserRequestModel
import com.example.api.models.PutUserRequestModel
import com.example.managers.SimpleManager
import io.ktor.http.*
import java.util.*

interface UserManager<Param, Request, Response> : SimpleManager<Param, Request, Response>

interface PutUserManager : UserManager<UUID, PutUserRequestModel, HttpStatusCode>

interface DeleteUserManager : UserManager<UUID, Unit, HttpStatusCode>

interface PostUserManager : UserManager<Unit, PostUserRequestModel, HttpStatusCode>

interface LoginUserManager : UserManager<Unit, LoginModel, UserIdModel>