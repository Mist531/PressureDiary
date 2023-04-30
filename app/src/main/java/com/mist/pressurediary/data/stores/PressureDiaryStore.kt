package com.mist.pressurediary.data.stores

import androidx.room.*
import com.mist.pressurediary.data.Database
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import kotlinx.serialization.Serializable
import kotlinx.uuid.UUID

object PressureDiaryStore {

    @Serializable //TODO: wtf is this?
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
    )

    @Dao
    interface PressureDiaryDao {
        @Insert(onConflict = OnConflictStrategy.REPLACE)
        suspend fun addNewEntry(newEntry: PressureDiaryTable)

        @get:Query("SELECT * FROM pressure_diary_info")
        val allEntry: List<PressureDiaryTable>

        @Update
        suspend fun updateEntry(entry: PressureDiaryTable)
    }

    @Transaction
    suspend fun addNewEntry(
        newEntry: PressureDiaryTable
    ) = withContext(Dispatchers.IO) {
        Database.localDatabase.pressureDiaryDao().addNewEntry(newEntry)
    }

    @Transaction
    suspend fun getAllEntry(): List<PressureDiaryTable> = withContext(Dispatchers.IO) {
        Database.localDatabase.pressureDiaryDao().allEntry
    }

    @Transaction
    suspend fun updateEntry(
        entry: PressureDiaryTable
    ) = withContext(Dispatchers.IO) {
        Database.localDatabase.pressureDiaryDao().updateEntry(entry)
    }
}