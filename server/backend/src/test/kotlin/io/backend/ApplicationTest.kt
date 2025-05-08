package io.backend

import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.statement.bodyAsText
import io.ktor.http.HttpStatusCode
import io.pressurediary.server.api.ApiRoutes
import io.pressurediary.server.api.models.DeviceType
import io.pressurediary.server.api.models.PostPressureRecordModel
import io.pressurediary.server.api.models.TokensModel
import io.pressurediary.server.backend.database.tables.DevicesTable
import io.pressurediary.server.backend.database.tables.HistoryTable
import io.pressurediary.server.backend.database.tables.NotificationsTable
import io.pressurediary.server.backend.database.tables.PressureRecordTagLinksTable
import io.pressurediary.server.backend.database.tables.PressureRecordsTable
import io.pressurediary.server.backend.database.tables.TagsTable
import io.pressurediary.server.backend.database.tables.UsersTable
import kotlinx.serialization.json.Json
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.After
import org.junit.jupiter.api.Order
import org.koin.core.context.stopKoin
import java.time.LocalDateTime
import kotlin.test.Test
import kotlin.test.assertEquals

class ApplicationTest {

    init {
        Database.connect(
            "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1;",
            driver = "org.h2.Driver"
        )

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

    @After
    fun closeTest(){
        stopKoin()
    }

    @Test
    @Order(1)
    fun healthCheck() = testMyApplication { myClient ->
        val response = myClient.get(ApiRoutes.HEALTHCHECK)
        assertEquals(HttpStatusCode.OK, response.status)
    }

    @Test
    @Order(2)
    fun registrationWithLogin() = testMyApplication { myClient ->
        val responseRegister = myClient.post(ApiRoutes.REGISTER_CREATE) {
            setBodyTest(testUser)
        }

        assertEquals(HttpStatusCode.OK, responseRegister.status)

        val responseAuth = myClient.post(
            ApiRoutes.LOGIN
        ) {
            setBodyTest(loginModel)
        }

        assertEquals(HttpStatusCode.OK, responseAuth.status)
    }

    @Test
    @Order(3)
    fun postPressureRecordWithGet() = testMyApplication { myClient ->
        val record = PostPressureRecordModel(
            dateTimeRecord = LocalDateTime.now(),
            systolic = 120,
            diastolic = 80,
            pulse = 60,
            deviceType = DeviceType.ANDROID,
        )

        val responseAuth = myClient.post(
            ApiRoutes.LOGIN
        ) {
            setBodyTest(loginModel)
        }

        val tokens = Json.decodeFromString<TokensModel>(responseAuth.bodyAsText())

        val responseAddRecord = myClient.post(
            ApiRoutes.PressureRecord.ADD
        ) {
            setBodyTest(
                body = record,
                token = tokens.access
            )
        }

        assertEquals(HttpStatusCode.Created, responseAddRecord.status)
    }
}


