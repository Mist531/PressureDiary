package com.backend

import com.backend.database.tables.PressureRecord
import com.backend.database.tables.PressureRecordsTable
import com.backend.database.tables.Tag
import com.backend.database.tables.TagsTable
import com.backend.database.tables.User
import com.backend.database.tables.UsersTable
import com.backend.managersImpl.PressureRecordManager
import com.backend.managersImpl.PressureRecordTagLinksManager
import com.backend.managersImpl.TagsManager
import com.backend.managersImpl.UserManager
import com.example.api.models.AddPressureRecordTagLinkModel
import com.example.api.models.AddTagModel
import com.example.api.models.DeviceType
import com.example.api.models.Gender
import com.example.api.models.PostPressureRecordModel
import com.example.api.models.PostUserRequestModel
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
    val mockPressureRecords = List(100){ index: Int ->
        PostPressureRecordModel(
            dateTimeRecord = LocalDateTime.now().minusDays(index.toLong()),
            systolic = Random.nextInt(100,145),
            diastolic = Random.nextInt(59,90),
            pulse = Random.nextInt(56,120),
            note = if (Random.nextBoolean()){
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