package com.example.workoutcompanion

import android.app.Application

class WorkoutCompanionApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        ExerciseRepository.initialize(this)
    }
}