package com.backend.managers.usersManager

import com.example.api.models.*
import com.example.managers.SimpleManager
import io.ktor.http.*
import io.ktor.server.application.*
import java.util.*

interface UserManager<Param, Request, Response> : SimpleManager<Param, Request, Response>

interface PutUserManager : UserManager<UUID, PutUserRequestModel, HttpStatusCode>

interface DeleteUserManager : UserManager<UUID, Unit, HttpStatusCode>

interface PostUserManager : UserManager<Unit, PostUserRequestModel, HttpStatusCode>

interface RefreshTokensManager : UserManager<Unit, RefreshTokenModel, TokensModel>

interface LoginUserManager : UserManager<Unit, LoginModel, UserIdModel>