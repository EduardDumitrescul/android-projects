package com.example.workoutcompanion2

import android.app.Application
import com.example.workoutcompanion2.database.AppRepository

class WorkoutCompanionApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        AppRepository.initialize(this   )
    }
}