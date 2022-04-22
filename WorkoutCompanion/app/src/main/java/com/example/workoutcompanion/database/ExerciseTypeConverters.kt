package com.example.workoutcompanion.database

import androidx.room.TypeConverter
import com.example.workoutcompanion.Exercise
import java.util.*

class ExerciseTypeConverters {

    @TypeConverter
    fun fromUUID(uuid: UUID?): String? {
        return uuid?.toString()
    }

    @TypeConverter
    fun toUUID(uuid: String?): UUID? {
        return UUID.fromString(uuid)
    }
}