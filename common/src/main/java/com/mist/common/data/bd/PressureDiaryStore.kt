package com.mist.common.data.bd

import androidx.room.ColumnInfo
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import arrow.core.some
import com.example.api.models.PressureRecordModel
import com.mist.common.models.PressureDiaryModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import kotlinx.uuid.UUID
import kotlinx.uuid.toJavaUUID
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

object PressureDiaryStore {

    @Entity(tableName = "pressure_diary_info")
    data class PressureDiaryTable(
        @PrimaryKey
        val id: UUID,
        @ColumnInfo
        val diastolic: Int,
        @ColumnInfo
        val systolic: Int,
        @ColumnInfo
        val pulse: Int,
        @ColumnInfo
        val date: LocalDate,
        @ColumnInfo
        val time: LocalTime,
        @ColumnInfo
        val comment: String
    ) {
        fun mapToModel() = PressureDiaryModel(
            id = id,
            diastolic = diastolic.some(),
            systolic = systolic.some(),
            pulse = pulse.some(),
            date = date,
            time = time,
            comment = comment
        )

        fun mapToPressureRecordModel() = PressureRecordModel(
            pressureRecordUUID = id.toJavaUUID(),
            diastolic = diastolic,
            systolic = systolic,
            pulse = pulse,
            dateTimeRecord = LocalDateTime.of(date, time),
            note = comment
        )
    }

    @Dao
    interface PressureDiaryDao {
        @Insert(onConflict = OnConflictStrategy.REPLACE)
        suspend fun addNewEntry(newEntry: PressureDiaryTable)

        @get:Query("SELECT * FROM pressure_diary_info ORDER BY date DESC, time DESC")
        val allEntry: Flow<List<PressureDiaryTable>>

        @Update
        suspend fun updateEntry(entry: PressureDiaryTable)

        @Delete
        suspend fun deleteEntry(entry: PressureDiaryTable)

        @Query("SELECT * FROM pressure_diary_info WHERE id = :id")
        suspend fun getEntryById(id: UUID): PressureDiaryTable
    }

    @Transaction
    suspend fun addNewEntry(
        newEntry: PressureDiaryTable
    ) = withContext(Dispatchers.IO) {
        Database.localDatabase.pressureDiaryDao().addNewEntry(newEntry)
    }

    @Transaction
    suspend fun getAllEntry(): Flow<List<PressureDiaryTable>> = withContext(Dispatchers.IO) {
        Database.localDatabase.pressureDiaryDao().allEntry
    }

    @Transaction
    suspend fun updateEntry(
        entry: PressureDiaryTable
    ) = withContext(Dispatchers.IO) {
        Database.localDatabase.pressureDiaryDao().updateEntry(entry)
    }

    @Transaction
    suspend fun deleteEntry(
        entry: PressureDiaryTable
    ) = withContext(Dispatchers.IO) {
        Database.localDatabase.pressureDiaryDao().deleteEntry(entry)
    }

    @Transaction
    suspend fun getEntryById(
        id: UUID
    ): PressureDiaryModel = withContext(Dispatchers.IO) {
        Database.localDatabase.pressureDiaryDao().getEntryById(id).mapToModel()
    }
}