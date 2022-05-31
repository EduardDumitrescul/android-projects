package com.example.workoutcompanion2.exercise

import androidx.lifecycle.ViewModel
import com.example.workoutcompanion2.database.AppRepository
import java.util.*

class ExerciseListViewModel : ViewModel() {
    private val repository = AppRepository.get()
    val exerciseListLiveData = repository.getExerciseAndPrimaryMuscleList()

    fun newExercise(): UUID {
        val exercise = Exercise.newInstance()
        repository.insertExercise(exercise)
        return exercise.id
    }

}