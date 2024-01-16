package com.backend

import com.backend.database.tables.*
import com.backend.managersImpl.PressureRecordManager
import com.backend.managersImpl.PressureRecordTagLinksManager
import com.backend.managersImpl.TagsManager
import com.backend.managersImpl.UserManager
import com.example.api.models.*
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.koin.java.KoinJavaComponent.getKoin
import java.time.LocalDate
import java.time.LocalDateTime

suspend fun createDefaultInfoUser(
    userEmail: String = "test@mail.ru",
    userPass: String = "test12",
    userManager: UserManager = getKoin().get(),
    pressureRecordManager: PressureRecordManager = getKoin().get(),
    tagManager: TagsManager = getKoin().get(),
    pressureRecordTagLinksManager: PressureRecordTagLinksManager = getKoin().get(),
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
                    mockPressureRecords.forEach { mockRecord ->
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
            } else {

            }
        }
    }
}