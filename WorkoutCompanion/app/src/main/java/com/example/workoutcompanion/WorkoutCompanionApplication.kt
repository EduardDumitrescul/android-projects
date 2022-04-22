package com.example.workoutcompanion

import android.app.Application
import androidx.room.RoomDatabase

class WorkoutCompanionApplication: Application() {
    lateinit var database: RoomDatabase

    override fun onCreate() {
        super.onCreate()
        ExerciseRepository.initialize(this)
    }
}