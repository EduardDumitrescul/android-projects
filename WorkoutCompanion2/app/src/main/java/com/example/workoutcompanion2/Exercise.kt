package com.example.workoutcompanion2

import kotlin.random.Random

class Exercise {
    var name: String = "name " + Random.nextInt().toString()
    var info: String = "info " + Random.nextInt().toString()

    companion object {
        fun newInstance(): Exercise = Exercise()
    }
}