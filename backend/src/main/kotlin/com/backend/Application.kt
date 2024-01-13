package com.backend

import com.backend.authorization.AuthRouteUtils
import com.backend.configure.configure
import com.backend.database.tables.*
import com.backend.managersImpl.*
import com.backend.modules.commonModule
import com.backend.modules.managerModule
import com.backend.modules.managersImplModule
import com.example.api.ApiRoutes
import com.example.api.models.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.swagger.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.koin.ktor.ext.inject
import java.io.File

fun main() {
    val url = System.getenv("DB_URL")
        ?: "jdbc:postgresql://db.kahfzyjuzapymwottfcj.supabase.co:5432/postgres"
    //?: "jdbc:postgresql://172.20.7.9:5432/db1095_05?currentSchema=kursach"
    val pass = System.getenv("DB_PASS")
        ?: "pwd1095..sad"
    //?: "pwd1095"
    val user = System.getenv("DB_USER")
        ?: "postgres"
    //?: "st1095"
    val port = System.getenv("PORT")?.toInt() ?: 8082
    val host = System.getenv("HOST") ?: "0.0.0.0"

    Database.connect(url, "org.postgresql.Driver", user, pass)

    CoroutineScope(Dispatchers.IO).launch {
        async {
            createTables()
        }.await()
        async {
            createTriggers()
        }.await()
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

suspend fun createTriggers() {
    val sqlFile = File("src/main/kotlin/com/backend/create.sql")
    val sqlScript = sqlFile.readText()

    newSuspendedTransaction(Dispatchers.IO) {
        exec(sqlScript)
    }
}

suspend fun createTables() {
    newSuspendedTransaction(Dispatchers.IO) {
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
                    ifRight = {
                        call.respond(
                            pressureRecordManager.getPaginatedPressureRecords(
                                call.receive<GetPaginatedPressureRecordsModel>()
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