package com.example.workoutcompanion2.exercise

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.workoutcompanion2.database.AppRepository
import java.util.*

private const val TAG = "ExerciseDetailViewModel"

class ExerciseDetailViewModel: ViewModel() {
    private val repository = AppRepository.get()
    private val exerciseIdLiveData = MutableLiveData<UUID>()

    var exerciseLiveData = Transformations.switchMap(exerciseIdLiveData) { exerciseId ->
        repository.getExercise(exerciseId)
    }

    fun loadExercise(id: UUID) {
        exerciseIdLiveData.value = id
    }

    fun saveExercise(exercise: Exercise) {
        repository.updateExercise(exercise)
    }

    fun deleteExercise(exercise: Exercise) {
        repository.deleteExerciseById(exercise.id)
    }

}