package com.example.workoutcompanion2.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.workoutcompanion2.exercise.ExerciseEntity
import com.example.workoutcompanion2.muscle.MuscleEntity

@Database(entities = [ExerciseEntity::class, MuscleEntity::class], version = 1)
@TypeConverters(DatabaseTypeConverters::class)
abstract class AppDatabase: RoomDatabase() {
    abstract fun appDao(): AppDao
}