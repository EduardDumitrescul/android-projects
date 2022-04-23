package com.example.workoutcompanion2.exercise

import android.app.Application
import androidx.lifecycle.ViewModel
import com.example.workoutcompanion2.database.AppRepository

class ExerciseListViewModel : ViewModel() {
    private val repository = AppRepository.get()
    val exerciseListLiveData = repository.getExerciseList()

}