package com.example.workoutcompanion2

import androidx.lifecycle.ViewModel

class MuscleListViewModel : ViewModel() {
    val muscleList: MutableList<Muscle> = mutableListOf()

    init {
        for(i in 0 until 100)
            muscleList += Muscle.newInstance()
    }
}