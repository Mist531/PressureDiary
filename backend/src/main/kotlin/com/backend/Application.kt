package com.backend

import com.backend.authorization.AuthRouteUtils
import com.backend.configure.configure
import com.backend.database.tables.*
import com.backend.managersImpl.*
import com.backend.modules.commonModule
import com.backend.modules.managerModule
import com.backend.modules.managersImplModule
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
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.koin.ktor.ext.inject

fun main() {
    val url = System.getenv("DB_URL")
        ?: "jdbc:postgresql://db.kahfzyjuzapymwottfcj.supabase.co:5432/postgres"
    val pass = System.getenv("DB_PASS")
        ?: "pwd1095..sad"
    val user = System.getenv("DB_USER")
        ?: "postgres"
    val port = System.getenv("PORT")?.toInt() ?: 8082
    val host = System.getenv("HOST") ?: "0.0.0.0"

    Database.connect(url, "org.postgresql.Driver", user, pass)

    CoroutineScope(Dispatchers.IO).launch {
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
                        userManager.postUser(call.receive())
                    )
                }
                post("login") {
                    call.respond(
                        userManager.login(call.receive())
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
                                        model = call.receive()
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
                                        call.receive()
                                    )
                                )
                            }
                        )
                    }
                    put {
                        authRouteUtils.authUser(
                            call = call,
                            ifRight = {
                                call.respond(pressureRecordManager.editPressureRecord(call.receive()))
                            }
                        )
                    }
                    get {
                        authRouteUtils.authUser(
                            call = call,
                            ifRight = {
                                call.respond(
                                    pressureRecordManager.getPaginatedPressureRecords(
                                        call.receive()
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
                                        model = call.receive()
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
                                    deviceManager.deleteUserDevice(call.receive())
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
                            ifRight = {
                                call.respond(
                                    userManager.deleteUser(call.receive())
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
                                        model = call.receive()
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
                                    tagsManager.getUserTagsList(id)
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
                                        addTagModel = call.receive()
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
                                    tagsManager.deleteUserTag(call.receive())
                                )
                            }
                        )
                    }
                    delete("deleteAll") {
                        authRouteUtils.authUser(
                            call = call,
                            ifRight = { id ->
                                call.respond(tagsManager.deleteAllTagsForUser(id))
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
                                        call.receive()
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
                                    call.receive()
                                )
                            }
                        )
                    }

                    delete("deleteByTag") {
                        authRouteUtils.authUser(
                            call = call,
                            ifRight = {
                                pressureRecordTagLinksManager.deletePressureRecordTagLinkByTag(
                                    call.receive()
                                )
                            }
                        )
                    }
                }
                //endregion
            }
        }
    }
}
