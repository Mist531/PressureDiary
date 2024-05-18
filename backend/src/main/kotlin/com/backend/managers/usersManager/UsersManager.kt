package com.backend.managers.usersManager

import com.backend.managers.SimpleManager
import com.example.api.models.LoginModel
import com.example.api.models.PostUserRequestModel
import com.example.api.models.PutUserRequestModel
import com.example.api.models.RefreshTokenModel
import com.example.api.models.TokensModel
import com.example.api.models.UserIdModel
import io.ktor.http.HttpStatusCode
import java.util.UUID

interface UserManager<Param, Request, Response> : SimpleManager<Param, Request, Response>

interface PutUserManager : UserManager<UUID, PutUserRequestModel, HttpStatusCode>

interface DeleteUserManager : UserManager<UUID, Unit, HttpStatusCode>

interface PostUserManager : UserManager<Unit, PostUserRequestModel, HttpStatusCode>

interface RefreshTokensManager : UserManager<Unit, RefreshTokenModel, TokensModel>

interface LoginUserManager : UserManager<Unit, LoginModel, UserIdModel>