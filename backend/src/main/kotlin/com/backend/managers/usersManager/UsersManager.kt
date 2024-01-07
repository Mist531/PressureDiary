package com.backend.managers.usersManager

import com.backend.authorization.models.LoginModel
import com.backend.authorization.models.UserIdModel
import com.backend.models.PostUserRequestModel
import com.backend.models.PutUserRequestModel
import com.example.managers.SimpleManager
import io.ktor.http.*
import java.util.*

interface UserManager<Param, Request, Response> : SimpleManager<Param, Request, Response>

interface PutUserManager : UserManager<UUID, PutUserRequestModel, HttpStatusCode>

interface DeleteUserManager : UserManager<UUID, Unit, HttpStatusCode>

interface PostUserManager : UserManager<Unit, PostUserRequestModel, HttpStatusCode>

interface LoginUserManager : UserManager<Unit, LoginModel, UserIdModel>