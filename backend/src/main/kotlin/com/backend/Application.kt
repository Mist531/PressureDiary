package com.backend

import com.backend.authorization.AuthRouteUtils
import com.example.api.models.LoginModel
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
import kotlinx.coroutines.launch
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.koin.ktor.ext.inject
import java.time.LocalDate
import java.time.LocalDateTime

fun main() {
    val url = System.getenv("DB_URL")
        ?: "jdbc:postgresql://db.kahfzyjuzapymwottfcj.supabase.co:5432/postgres"
    //?: "jdbc:postgresql://172.20.7.9:5432/db1095_05"
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
        createTables()
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
                                id = id,
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
                                id = id,
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
                                id = id
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
                                id = id,
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
                                userUUID = id
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
                                userUUID = id,
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
                                userUUID = id
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
        }
    }
}

suspend fun createDefaultInfoUser(
    userEmail: String = "test",
    userPass: String = "test",
) {
    val mockPressureRecords = listOf(
        PostPressureRecordModel(
            dateTimeRecord = LocalDateTime.parse("2024-01-02T17:28:43.524462"),
            systolic = 134,
            diastolic = 83,
            pulse = 86,
            note = "Random note 29",
            deviceType = DeviceType.ANDROID
        ),
        PostPressureRecordModel(
            dateTimeRecord = LocalDateTime.parse("2024-01-09T17:28:43.524502"),
            systolic = 115,
            diastolic = 70,
            pulse = 69,
            note = "Random note 9",
            deviceType = DeviceType.ANDROID
        ),
        PostPressureRecordModel(
            dateTimeRecord = LocalDateTime.parse("2023-12-31T17:28:43.524520"),
            systolic = 126,
            diastolic = 82,
            pulse = 61,
            note = "Random note 3",
            deviceType = DeviceType.ANDROID
        ),
        PostPressureRecordModel(
            dateTimeRecord = LocalDateTime.parse("2023-12-15T17:28:43.524536"),
            systolic = 132,
            diastolic = 87,
            pulse = 62,
            note = "Random note 58",
            deviceType = DeviceType.ANDROID
        ),
        PostPressureRecordModel(
            dateTimeRecord = LocalDateTime.parse("2023-12-25T17:28:43.524552"),
            systolic = 140,
            diastolic = 90,
            pulse = 74,
            note = "Random note 79",
            deviceType = DeviceType.ANDROID
        ),
        PostPressureRecordModel(
            dateTimeRecord = LocalDateTime.parse("2023-12-25T17:28:43.524568"),
            systolic = 135,
            diastolic = 87,
            pulse = 100,
            note = "Random note 8",
            deviceType = DeviceType.ANDROID
        )
    )

    newSuspendedTransaction(Dispatchers.IO) {
        User.find(UsersTable.email eq userEmail).empty().let { boolean: Boolean ->
            if (boolean) {
                User.new {
                    email = userEmail
                    password = userPass
                    firstName = "test"
                    lastName = "test"
                    dateOfBirth = LocalDate.now().minusYears(20)
                    gender = Gender.M
                    timeZone = null
                }.let { userInfo ->
                    Device.new {
                        userUUID = userInfo
                        deviceType = DeviceType.ANDROID
                        lastSyncDate = LocalDate.now()
                    }
                    mockPressureRecords.map { mockRecord ->
                        PressureRecord.new {
                            userUUID = userInfo
                            dateTimeRecord = mockRecord.dateTimeRecord
                            systolic = mockRecord.systolic
                            diastolic = mockRecord.diastolic
                            pulse = mockRecord.pulse
                            note = mockRecord.note
                            deviceType = mockRecord.deviceType
                        }
                    }.let { pressureRecords ->
                        Tag.new {
                            userUUID = userInfo
                            name = "test"
                        }.let { tag ->
                            PressureRecordTagLinksTable.insert {
                                it[pressureRecordUUID] = pressureRecords.random().id
                                it[tagUUID] = tag.id
                            }
                        }
                    }
                }
            }
        }
    }
}

suspend fun createTables() {
    newSuspendedTransaction(Dispatchers.IO) {
        SchemaUtils.createMissingTablesAndColumns(
            DevicesTable,
            HistoryTable,
            NotificationsTable,
            PressureRecordsTable,
            PressureRecordTagLinksTable,
            TagsTable,
            UsersTable
        )
    }
}

//region OldRouting
/* routing {
     route("api") {
         swaggerUI(path = "docs", swaggerFile = "openapi/documentation.yaml")
         route("healthcheck") {
             get {
                 call.respond(HttpStatusCode.OK)
             }
         }
         route("register") {
             post("create") {
                 call.respond(
                     userManager.postUser(call.receive<PostUserRequestModel>())
                 )
             }
             post("login") {
                 call.respond(
                     userManager.login(call.receive<LoginModel>())
                 )
             }
         }
         authenticate("jwt") {
             //region PressureRecord
             route("pressureRecord") {
                 post {
                     authRouteUtils.authUser(
                         call = call,
                         ifRight = { id ->
                             call.respond(
                                 pressureRecordManager.addPressureRecord(
                                     id = id,
                                     model = call.receive<PostPressureRecordModel>()
                                 )
                             )
                         }
                     )
                 }
                 delete {
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
                 put {
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
                 get {
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
             }
             //endregion

             //region Device
             route("device") {
                 post {
                     authRouteUtils.authUser(
                         call = call,
                         ifRight = { id ->
                             call.respond(
                                 deviceManager.addDeviceForUser(
                                     id = id,
                                     model = call.receive<PostDeviceForUserModel>()
                                 )
                             )
                         }
                     )
                 }
                 get {
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
                 delete {
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
             }
             //endregion

             //region User
             route("user") {
                 delete("delete") {
                     authRouteUtils.authUser(
                         call = call,
                         ifRight = { id ->
                             call.respond(
                                 userManager.deleteUser(
                                     id = id
                                 )
                             )
                         }
                     )
                 }
                 put("edit") {
                     authRouteUtils.authUser(
                         call = call,
                         ifRight = { id ->
                             call.respond(
                                 userManager.putUser(
                                     id = id,
                                     model = call.receive<PutUserRequestModel>()
                                 )
                             )
                         }
                     )
                 }
             }
             //endregion

             //region Tags
             route("tags") {
                 get {
                     authRouteUtils.authUser(
                         call = call,
                         ifRight = { id ->
                             call.respond(
                                 tagsManager.getUserTagsList(
                                     userUUID = id
                                 )
                             )
                         }
                     )
                 }
                 post {
                     authRouteUtils.authUser(
                         call = call,
                         ifRight = { id ->
                             call.respond(
                                 tagsManager.addTagForUser(
                                     userUUID = id,
                                     addTagModel = call.receive<AddTagModel>()
                                 )
                             )
                         }
                     )
                 }
                 delete {
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
                 delete("deleteAll") {
                     authRouteUtils.authUser(
                         call = call,
                         ifRight = { id ->
                             call.respond(
                                 tagsManager.deleteAllTagsForUser(
                                     userUUID = id
                                 )
                             )
                         }
                     )
                 }
             }
             //endregion

             //region PressureRecordTagLinks
             route("pressureRecordTagLinks") {
                 post {
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
                 delete("deleteByRecord") {
                     authRouteUtils.authUser(
                         call = call,
                         ifRight = {
                             pressureRecordTagLinksManager.deletePressureRecordTagLinkByRecord(
                                 call.receive<DeletePressureRecordTagLinkByRecordModel>()
                             )
                         }
                     )
                 }
                 delete("deleteByTag") {
                     authRouteUtils.authUser(
                         call = call,
                         ifRight = {
                             pressureRecordTagLinksManager.deletePressureRecordTagLinkByTag(
                                 call.receive<DeletePressureRecordTagLinkByTagModel>()
                             )
                         }
                     )
                 }
             }
             //endregion
         }
     }
 }*/
//endregion