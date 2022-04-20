package com.example.workoutcompanion

import androidx.lifecycle.ViewModel

class ExerciseListViewModel: ViewModel() {

    private val exerciseRepository = ExerciseRepository.get()
    var exerciseListLiveData = exerciseRepository.getExercises()

    fun reload() {
        exerciseListLiveData = exerciseRepository.getExercises()
    }

}