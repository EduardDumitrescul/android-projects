package com.example.workoutcompanion2.muscle

import androidx.lifecycle.ViewModel

class MuscleDetailViewModel : ViewModel() {
    var muscle: Muscle = Muscle.newInstance()

    fun load(muscle: Muscle) {
        this.muscle = muscle
    }

    fun save() {

    }
}