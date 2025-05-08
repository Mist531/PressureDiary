package io.pressurediary.server.backend

import arrow.fx.coroutines.parMap
import io.pressurediary.server.api.models.AddPressureRecordTagLinkModel
import io.pressurediary.server.api.models.AddTagModel
import io.pressurediary.server.api.models.DeviceType
import io.pressurediary.server.api.models.Gender
import io.pressurediary.server.api.models.PostPressureRecordModel
import io.pressurediary.server.api.models.PostUserRequestModel
import io.pressurediary.server.backend.database.tables.PressureRecord
import io.pressurediary.server.backend.database.tables.PressureRecordsTable
import io.pressurediary.server.backend.database.tables.Tag
import io.pressurediary.server.backend.database.tables.TagsTable
import io.pressurediary.server.backend.database.tables.User
import io.pressurediary.server.backend.database.tables.UsersTable
import io.pressurediary.server.backend.managersImpl.PressureRecordManager
import io.pressurediary.server.backend.managersImpl.PressureRecordTagLinksManager
import io.pressurediary.server.backend.managersImpl.TagsManager
import io.pressurediary.server.backend.managersImpl.UserManager
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.koin.java.KoinJavaComponent.getKoin
import java.time.LocalDate
import java.time.LocalDateTime
import kotlin.random.Random

suspend fun createDefaultInfoUser(
    userEmail: String = "test@mail.ru",
    userPass: String = "test12",
    userManager: UserManager = getKoin().get(),
    pressureRecordManager: PressureRecordManager = getKoin().get(),
    tagManager: TagsManager = getKoin().get(),
    pressureRecordTagLinksManager: PressureRecordTagLinksManager = getKoin().get(),
) {
    val mockPressureRecords = List(100) { index: Int ->
        PostPressureRecordModel(
            dateTimeRecord = LocalDateTime.now().minusDays(index.toLong()),
            systolic = Random.nextInt(100, 145),
            diastolic = Random.nextInt(59, 90),
            pulse = Random.nextInt(56, 120),
            note = if (Random.nextBoolean()) {
                "Random note $index"
            } else "",
            deviceType = DeviceType.ANDROID
        )
    }

    newSuspendedTransaction(Dispatchers.IO) {
        User.find(UsersTable.email eq userEmail).let { user ->
            if (user.empty()) {
                userManager.postUser(
                    PostUserRequestModel(
                        email = userEmail,
                        password = userPass,
                        firstName = "test",
                        lastName = "test",
                        dateOfBirth = LocalDate.now().minusYears(20),
                        gender = Gender.M,
                        timeZone = null,
                    )
                )
                User.find {
                    UsersTable.email eq userEmail
                }.firstOrNull()?.let { userInfo ->
                    mockPressureRecords.parMap { mockRecord ->
                        pressureRecordManager.addPressureRecord(
                            userInfo.id.value,
                            mockRecord
                        )
                    }
                    tagManager.addTagForUser(
                        userInfo.id.value,
                        AddTagModel(
                            name = "test"
                        )
                    )
                    PressureRecord.find {
                        PressureRecordsTable.userUUID eq userInfo.id.value
                    }.firstOrNull()?.let { pressureRecord ->
                        Tag.find {
                            TagsTable.userUUID eq userInfo.id.value
                        }.firstOrNull()?.let { tag ->
                            pressureRecordTagLinksManager.addPressureRecordTagLink(
                                addPressureRecordTagLinkModel = AddPressureRecordTagLinkModel(
                                    pressureRecordUUID = pressureRecord.id.value,
                                    tagUUID = tag.id.value
                                )
                            )
                        }
                    }
                }
            }
        }
    }
}