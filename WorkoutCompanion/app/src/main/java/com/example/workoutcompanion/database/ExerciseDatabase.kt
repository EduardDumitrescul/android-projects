package com.example.workoutcompanion.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.example.workoutcompanion.Exercise

@Database(entities = [Exercise::class], version = 1)
@TypeConverters(ExerciseTypeConverters::class)
abstract class ExerciseDatabase: RoomDatabase() {

    abstract fun exerciseDao(): ExerciseDao
}