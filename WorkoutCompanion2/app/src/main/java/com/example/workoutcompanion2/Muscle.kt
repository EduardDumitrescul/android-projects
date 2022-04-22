package com.example.workoutcompanion2

import kotlin.random.Random

class Muscle {
    var name: String = "name " + Random.nextInt().toString()

    companion object {
        fun newInstance(): Muscle = Muscle()
    }
}