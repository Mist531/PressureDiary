package com.backend

import com.backend.authorization.AuthRouteUtils
import com.backend.configure.configure
import com.backend.database.tables.DevicesTable
import com.backend.database.tables.HistoryTable
import com.backend.database.tables.NotificationsTable
import com.backend.database.tables.PressureRecordTagLinksTable
import com.backend.database.tables.PressureRecordsTable
import com.backend.database.tables.TagsTable
import com.backend.database.tables.UsersTable
import com.backend.managersImpl.DeviceManager
import com.backend.managersImpl.HistoryManager
import com.backend.managersImpl.NotificationsManager
import com.backend.managersImpl.PressureRecordManager
import com.backend.managersImpl.PressureRecordTagLinksManager
import com.backend.managersImpl.TagsManager
import com.backend.managersImpl.UserManager
import com.backend.modules.commonModule
import com.backend.modules.managerModule
import com.backend.modules.managersImplModule
import com.example.api.ApiRoutes
import com.example.api.models.AddPressureRecordTagLinkModel
import com.example.api.models.AddTagModel
import com.example.api.models.DeletePressureRecordModel
import com.example.api.models.DeletePressureRecordTagLinkByRecordModel
import com.example.api.models.DeletePressureRecordTagLinkByTagModel
import com.example.api.models.DeleteUserDeviceModel
import com.example.api.models.DeleteUserTagModel
import com.example.api.models.GetAllPressureRecordsModel
import com.example.api.models.GetHistoryPressureRecordModel
import com.example.api.models.GetPaginatedPressureRecordsModel
import com.example.api.models.LoginModel
import com.example.api.models.PostDeviceForUserModel
import com.example.api.models.PostPressureRecordModel
import com.example.api.models.PostUserRequestModel
import com.example.api.models.PutPressureRecordModel
import com.example.api.models.PutUserRequestModel
import com.example.api.models.RefreshTokenModel
import com.example.api.models.RestoreHistoryModel
import com.example.api.models.UpdateNotificationModel
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.application.call
import io.ktor.server.auth.authenticate
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.server.plugins.swagger.swaggerUI
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.delete
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.put
import io.ktor.server.routing.route
import io.ktor.server.routing.routing
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import org.koin.ktor.ext.inject
import java.io.File

data class BdConnectInfo(
    val url: String,
    val bdPass: String,
    val bdUser: String,
)

enum class BdConnect(val bdConnectInfo: BdConnectInfo) {
    NovGu(
        BdConnectInfo(
            url = "jdbc:postgresql://172.20.7.9:5432/db1095_05?currentSchema=kursach",
            bdPass = "pwd1095",
            bdUser = "st1095"
        )
    ),
    Supabase(
        BdConnectInfo(
            url = "jdbc:postgresql://aws-0-eu-central-1.pooler.supabase.com:5432/postgres",
            bdPass = "pwd1095..sad",
            bdUser = "postgres.kahfzyjuzapymwottfcj"
        )
    )
}

fun main() {
    val connectInfo = BdConnect.Supabase
    val port = System.getenv("PORT")?.toInt() ?: 8082
    val host = System.getenv("HOST") ?: "0.0.0.0"

    with(connectInfo.bdConnectInfo) {
        Database.connect(
            url = url,
            driver = "org.postgresql.Driver",
            user = bdUser,
            password = bdPass
        )
    }

    CoroutineScope(Dispatchers.IO + SupervisorJob()).launch {
        //createTables()
        //createTriggers()
        //createViews()
        //createProcedure()
        delay(10000)
        createDefaultInfoUser()
    }

    embeddedServer(
        Netty,
        port = port,
        host = host,
        module = {
            myApplicationModule()
        }
    ).start(wait = true)
}

fun createProcedure() {
    val sqlFile = File("src/main/kotlin/com/backend/procedure.sql")
    val sqlScript = sqlFile.readText()

    transaction {
        exec(sqlScript)
    }
}

fun createViews() {
    val sqlFile = File("src/main/kotlin/com/backend/views.sql")
    val sqlScript = sqlFile.readText()

    transaction {
        exec(sqlScript)
    }
}

fun createTriggers() {
    val sqlFile = File("src/main/kotlin/com/backend/triggers.sql")
    val sqlScript = sqlFile.readText()

    transaction {
        exec(sqlScript)
    }
}

fun createTables() {
    transaction {
        SchemaUtils.createMissingTablesAndColumns(
            HistoryTable,
            NotificationsTable,
            PressureRecordsTable,
            PressureRecordTagLinksTable,
            TagsTable,
            UsersTable,
            DevicesTable
        )
    }
}

fun Application.myApplicationModule() {

    configure(
        listModules = listOf(
            managerModule, managersImplModule, commonModule
        )
    )

    val pressureRecordManager: PressureRecordManager by inject()
    val deviceManager: DeviceManager by inject()
    val authRouteUtils: AuthRouteUtils by inject()
    val userManager: UserManager by inject()
    val tagsManager: TagsManager by inject()
    val pressureRecordTagLinksManager: PressureRecordTagLinksManager by inject()
    val historyManager: HistoryManager by inject()
    val notificationsManager: NotificationsManager by inject()

    routing {
        route(ApiRoutes.BASE) {
            swaggerUI(path = "docs", swaggerFile = "openapi/documentation.yaml")
        }
        get(ApiRoutes.HEALTHCHECK) {
            call.respond(HttpStatusCode.OK)
        }
        post(ApiRoutes.REGISTER_CREATE) {
            call.respond(
                userManager.postUser(call.receive<PostUserRequestModel>())
            )
        }
        post(ApiRoutes.LOGIN) {
            call.respond(
                userManager.login(call.receive<LoginModel>())
            )
        }
        post(ApiRoutes.REFRESH_TOKEN) {
            call.respond(
                userManager.refreshToken(call.receive<RefreshTokenModel>())
            )
        }
        authenticate("jwt") {
            post(ApiRoutes.PressureRecord.ADD) {
                authRouteUtils.authUser(
                    call = call,
                    ifRight = { id ->
                        call.respond(
                            pressureRecordManager.addPressureRecord(
                                userId = id,
                                model = call.receive<PostPressureRecordModel>()
                            )
                        )
                    }
                )
            }
            delete(ApiRoutes.PressureRecord.DELETE) {
                authRouteUtils.authUser(
                    call = call,
                    ifRight = {
                        call.respond(
                            pressureRecordManager.deletePressureRecord(
                                call.receive<DeletePressureRecordModel>()
                            )
                        )
                    }
                )
            }
            put(ApiRoutes.PressureRecord.EDIT) {
                authRouteUtils.authUser(
                    call = call,
                    ifRight = {
                        call.respond(
                            pressureRecordManager.editPressureRecord(
                                call.receive<PutPressureRecordModel>()
                            )
                        )
                    }
                )
            }
            get(ApiRoutes.PressureRecord.GET_PAGINATED) {
                authRouteUtils.authUser(
                    call = call,
                    ifRight = { id ->
                        call.respond(
                            pressureRecordManager.getPaginatedPressureRecords(
                                userId = id,
                                model = call.receive<GetPaginatedPressureRecordsModel>()
                            )
                        )
                    }
                )
            }
            get(ApiRoutes.PressureRecord.GET_ALL) {
                authRouteUtils.authUser(
                    call = call,
                    ifRight = { id ->
                        call.respond(
                            pressureRecordManager.getAllPressureRecords(
                                userId = id,
                                model = call.receive<GetAllPressureRecordsModel>()
                            )
                        )
                    }
                )
            }
            post(ApiRoutes.Device.ADD) {
                authRouteUtils.authUser(
                    call = call,
                    ifRight = { id ->
                        call.respond(
                            deviceManager.addDeviceForUser(
                                userId = id,
                                model = call.receive<PostDeviceForUserModel>()
                            )
                        )
                    }
                )
            }
            get(ApiRoutes.Device.GET_ALL) {
                authRouteUtils.authUser(
                    call = call,
                    ifRight = { id ->
                        call.respond(
                            deviceManager.getUserDevicesList(
                                id
                            )
                        )
                    }
                )
            }
            delete(ApiRoutes.Device.DELETE) {
                authRouteUtils.authUser(
                    call = call,
                    ifRight = {
                        call.respond(
                            deviceManager.deleteUserDevice(
                                call.receive<DeleteUserDeviceModel>()
                            )
                        )
                    }
                )
            }
            delete(ApiRoutes.User.DELETE) {
                authRouteUtils.authUser(
                    call = call,
                    ifRight = { id ->
                        call.respond(
                            userManager.deleteUser(
                                userId = id
                            )
                        )
                    }
                )
            }
            put(ApiRoutes.User.EDIT) {
                authRouteUtils.authUser(
                    call = call,
                    ifRight = { id ->
                        call.respond(
                            userManager.putUser(
                                userId = id,
                                model = call.receive<PutUserRequestModel>()
                            )
                        )
                    }
                )
            }
            get(ApiRoutes.Tags.GET) {
                authRouteUtils.authUser(
                    call = call,
                    ifRight = { id ->
                        call.respond(
                            tagsManager.getUserTagsList(
                                userId = id
                            )
                        )
                    }
                )
            }
            post(ApiRoutes.Tags.ADD) {
                authRouteUtils.authUser(
                    call = call,
                    ifRight = { id ->
                        call.respond(
                            tagsManager.addTagForUser(
                                userId = id,
                                addTagModel = call.receive<AddTagModel>()
                            )
                        )
                    }
                )
            }
            delete(ApiRoutes.Tags.DELETE) {
                authRouteUtils.authUser(
                    call = call,
                    ifRight = {
                        call.respond(
                            tagsManager.deleteUserTag(
                                call.receive<DeleteUserTagModel>()
                            )
                        )
                    }
                )
            }
            delete(ApiRoutes.Tags.DELETE_ALL) {
                authRouteUtils.authUser(
                    call = call,
                    ifRight = { id ->
                        call.respond(
                            tagsManager.deleteAllTagsForUser(
                                userId = id
                            )
                        )
                    }
                )
            }
            post(ApiRoutes.PressureRecordTagLinks.ADD) {
                authRouteUtils.authUser(
                    call = call,
                    ifRight = {
                        call.respond(
                            pressureRecordTagLinksManager.addPressureRecordTagLink(
                                call.receive<AddPressureRecordTagLinkModel>()
                            )
                        )
                    }
                )
            }
            delete(ApiRoutes.PressureRecordTagLinks.DELETE_BY_RECORD) {
                authRouteUtils.authUser(
                    call = call,
                    ifRight = {
                        pressureRecordTagLinksManager.deletePressureRecordTagLinkByRecord(
                            call.receive<DeletePressureRecordTagLinkByRecordModel>()
                        )
                    }
                )
            }
            delete(ApiRoutes.PressureRecordTagLinks.DELETE_BY_TAG) {
                authRouteUtils.authUser(
                    call = call,
                    ifRight = {
                        pressureRecordTagLinksManager.deletePressureRecordTagLinkByTag(
                            call.receive<DeletePressureRecordTagLinkByTagModel>()
                        )
                    }
                )
            }
            get(ApiRoutes.History.GET_HISTORY) {
                authRouteUtils.authUser(
                    call = call,
                    ifRight = {
                        call.respond(
                            historyManager.getHistoryForRecordManagerImpl(
                                call.receive<GetHistoryPressureRecordModel>()
                            )
                        )
                    }
                )
            }
            post(ApiRoutes.History.RESTORE_FROM_HISTORY) {
                authRouteUtils.authUser(
                    call = call,
                    ifRight = { id ->
                        call.respond(
                            historyManager.restoreRecordFromHistory(
                                userId = id,
                                model = call.receive<RestoreHistoryModel>()
                            )
                        )
                    }
                )
            }
            get(ApiRoutes.Notifications.GET_ALL) {
                authRouteUtils.authUser(
                    call = call,
                    ifRight = { id ->
                        call.respond(
                            notificationsManager.getAllNotifications(
                                userId = id
                            )
                        )
                    }
                )
            }
            put(ApiRoutes.Notifications.UPDATE) {
                authRouteUtils.authUser(
                    call = call,
                    ifRight = {
                        call.respond(
                            notificationsManager.updateNotification(
                                call.receive<UpdateNotificationModel>()
                            )
                        )
                    }
                )
            }
            get(ApiRoutes.Notifications.GET_NEXT) {
                authRouteUtils.authUser(
                    call = call,
                    ifRight = { id ->
                        call.respond(
                            notificationsManager.getNextNotification(
                                userId = id
                            )
                        )
                    }
                )
            }
        }
    }
}