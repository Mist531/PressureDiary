package io.pressurediary.server.backend.managers.usersManager

import io.pressurediary.server.backend.managers.SimpleManager
import io.pressurediary.server.api.models.LoginModel
import io.pressurediary.server.api.models.PostUserRequestModel
import io.pressurediary.server.api.models.PutUserRequestModel
import io.pressurediary.server.api.models.RefreshTokenModel
import io.pressurediary.server.api.models.TokensModel
import io.pressurediary.server.api.models.UserIdModel
import io.ktor.http.HttpStatusCode
import java.util.UUID

interface UserManager<Param, Request, Response> : SimpleManager<Param, Request, Response>

interface PutUserManager : UserManager<UUID, PutUserRequestModel, HttpStatusCode>

interface DeleteUserManager : UserManager<UUID, Unit, HttpStatusCode>

interface PostUserManager : UserManager<Unit, PostUserRequestModel, HttpStatusCode>

interface RefreshTokensManager : UserManager<Unit, RefreshTokenModel, TokensModel>

interface LoginUserManager : UserManager<Unit, LoginModel, UserIdModel>