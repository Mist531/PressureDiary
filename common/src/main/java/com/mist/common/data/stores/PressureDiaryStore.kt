package com.mist.common.data.stores

import androidx.room.*
import arrow.core.some
import com.mist.common.models.PressureDiaryModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import kotlinx.uuid.UUID
import java.time.LocalDate
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