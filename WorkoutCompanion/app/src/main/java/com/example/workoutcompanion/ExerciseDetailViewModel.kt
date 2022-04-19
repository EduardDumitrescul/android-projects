package com.example.workoutcompanion

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import java.util.*

class ExerciseDetailViewModel(): ViewModel() {
    private val exerciseRepository = ExerciseRepository.get()
    private val exerciseIdLiveData = MutableLiveData<UUID>()

    var exerciseLiveData: LiveData<Exercise?> =
        Transformations.switchMap(exerciseIdLiveData) { exerciseID ->
            exerciseRepository.getExercise(exerciseID)
        }

    fun loadExercise(exerciseId: UUID) {
        exerciseIdLiveData.value = exerciseId
    }

    fun saveExercise(exercise: Exercise) {
        exerciseRepository.saveExercise(exercise)
    }

}