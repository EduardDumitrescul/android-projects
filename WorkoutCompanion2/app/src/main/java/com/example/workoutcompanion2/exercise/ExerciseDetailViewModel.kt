package com.example.workoutcompanion2.exercise

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.workoutcompanion2.database.AppRepository
import java.util.*

private const val TAG = "ExerciseDetailViewModel"

class ExerciseDetailViewModel : ViewModel() {
    private val repository = AppRepository.get()
    private val exerciseIdLiveData = MutableLiveData<UUID>()
    var muscleListLiveData = repository.getMuscleList()

    var exerciseLiveData = Transformations.switchMap(exerciseIdLiveData) { exerciseId ->
        repository.getExerciseAndPrimaryMuscle(exerciseId)
    }

    fun loadExercise(id: UUID) {
        exerciseIdLiveData.value = id
    }

    fun saveExercise(exercise: Exercise) {
        exercise.primaryMuscle?.name?.let { Log.d(TAG, it) }
        exercise.primaryMuscle?.id?.let { Log.d(TAG, it.toString()) }
        repository.updateExercise(exercise)
    }

    fun deleteExercise(exercise: Exercise) {
        repository.deleteExerciseById(exercise.id)
    }

}