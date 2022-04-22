package com.example.workoutcompanion2.exercise

import androidx.lifecycle.ViewModel

class ExerciseDetailViewModel: ViewModel() {
    lateinit var exercise: Exercise

    fun loadExercise(exercise: Exercise) {
        this.exercise = exercise
    }

    fun saveExrecise() {

    }

}