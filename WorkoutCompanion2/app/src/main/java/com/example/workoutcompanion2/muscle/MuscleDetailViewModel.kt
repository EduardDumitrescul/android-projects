package com.example.workoutcompanion2.muscle

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.workoutcompanion2.database.AppRepository
import java.util.*

class MuscleDetailViewModel : ViewModel() {
    private val repository = AppRepository.get()
    private val muscleIdLiveData = MutableLiveData<UUID>()

    val muscleLiveData = Transformations.switchMap(muscleIdLiveData) {muscleId ->
        repository.getMuscle(muscleId)
    }

    fun loadMuscle(id: UUID) {
        muscleIdLiveData.value = id
    }

    fun saveMuscle(muscle: Muscle) {
        repository.updateMuscle(muscle)
    }

    fun deleteMuscle(muscle: Muscle) {
        repository.deleteMuscleById(muscle.id)
    }
}