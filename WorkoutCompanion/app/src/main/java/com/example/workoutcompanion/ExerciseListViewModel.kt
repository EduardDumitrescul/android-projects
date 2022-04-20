package com.example.workoutcompanion

import androidx.lifecycle.ViewModel

class ExerciseListViewModel: ViewModel() {

    private val exerciseRepository = ExerciseRepository.get()
    val exerciseListLiveData = exerciseRepository.getExercises()


}