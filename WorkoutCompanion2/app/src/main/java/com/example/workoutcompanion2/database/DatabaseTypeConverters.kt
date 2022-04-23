package com.example.workoutcompanion2.database

import androidx.room.TypeConverter
import java.util.*

class DatabaseTypeConverters {
    @TypeConverter
    fun fromUUID(id: UUID?): String? = id?.toString()

    @TypeConverter
    fun toUUID(id: String?): UUID? = UUID.fromString(id)
}