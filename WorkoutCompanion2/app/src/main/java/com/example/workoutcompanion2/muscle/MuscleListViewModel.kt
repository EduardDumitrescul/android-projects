package com.example.workoutcompanion2.muscle

import androidx.lifecycle.ViewModel
import com.example.workoutcompanion2.database.AppRepository
import java.util.*

class MuscleListViewModel : ViewModel() {
    private val repository = AppRepository.get()
    val muscleListLiveData = repository.getMuscleList()

    fun newMuscle(): UUID {
        val muscle = Muscle.newInstance()
        repository.insertMuscle(muscle)
        return muscle.id
    }
}