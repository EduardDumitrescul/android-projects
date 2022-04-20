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

    @TypeConverter
    fun fromExercise(exercise: ExerciseEntity): ExerciseEntity {
        return ExerciseEntity(exercise.id_exercise, exercise.name, exercise.equipment)
    }

    @TypeConverter
    fun toExercise(exerciseEntity: ExerciseEntity): Exercise {
        return Exercise.newInstance(exerciseEntity.id_exercise, exerciseEntity.name, exerciseEntity.equipment)
    }
}