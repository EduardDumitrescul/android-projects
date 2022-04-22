package com.example.workoutcompanion2

import androidx.lifecycle.ViewModel

class ExerciseListViewModel : ViewModel() {
    val exerciseList: MutableList<Exercise> = mutableListOf()

    init {
        for(i in 0 until 100)
            exerciseList += Exercise.newInstance()
    }

}