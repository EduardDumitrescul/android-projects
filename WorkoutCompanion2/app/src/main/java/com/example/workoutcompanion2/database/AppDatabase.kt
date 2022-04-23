package com.example.workoutcompanion2.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.workoutcompanion2.exercise.ExerciseEntity

@Database(entities = [ExerciseEntity::class], version = 1)
@TypeConverters(DatabaseTypeConverters::class)
abstract class AppDatabase: RoomDatabase() {
    abstract fun appDao(): AppDao
}