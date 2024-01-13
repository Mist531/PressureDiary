package com.mist.common.data.bd

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import org.koin.core.component.KoinComponent

@Database(
    entities = [
        PressureDiaryStore.PressureDiaryTable::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(
    StoreConverters.UUIDConverter::class,
    StoreConverters.LocalDateConverter::class,
    StoreConverters.LocalTimeConverter::class
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun pressureDiaryDao(): PressureDiaryStore.PressureDiaryDao
}

object Database : KoinComponent {
    lateinit var localDatabase: AppDatabase

    fun initLocalDatabase(context: Context) {
        localDatabase = Room.databaseBuilder(
            context,
            AppDatabase::class.java, "pressureDiaryDatabase"
        ).build()
    }
}