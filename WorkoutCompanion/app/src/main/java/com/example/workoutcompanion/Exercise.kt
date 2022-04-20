package com.example.workoutcompanion

import java.util.*


class Exercise() {
    var id_exercise: UUID = UUID.randomUUID()
    var name: String = ""
    var equipment: String = ""


    companion object {
        fun newInstance(
            id_exercise: UUID,
            name: String,
            equipment: String
        ): Exercise {
            return Exercise().apply {
                this.id_exercise = id_exercise
                this.equipment = equipment
                this.name = name
            }
        }
    }
}