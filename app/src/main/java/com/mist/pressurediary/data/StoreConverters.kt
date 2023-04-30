package com.mist.pressurediary.data

import androidx.room.TypeConverter
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import kotlinx.uuid.UUID

interface StoreConverters {
    class UUIDConverter {
        @TypeConverter
        fun uuidToString(uuid: UUID?): String? =
            uuid?.toString()

        @TypeConverter
        fun stringToUUID(string: String?): UUID? = string?.let {
            UUID(it)
        }
    }

    class LocalDateConverter {
        @TypeConverter
        fun dateToString(date: LocalDate?): String? =
            date?.toString()

        @TypeConverter
        fun stringToDate(string: String?): LocalDate? = string?.let {
            LocalDate.parse(it)
        }
    }

    class LocalTimeConverter {
        @TypeConverter
        fun dateToString(time: LocalTime?): String? =
            time?.toString()

        @TypeConverter
        fun stringToTime(string: String?): LocalTime? = string?.let {
            LocalTime.parse(it)
        }
    }
}

