package com.example.workoutcompanion2.exercise

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

class Exercise {
    var id: UUID = UUID.randomUUID()
    var name: String = "Exercise Name "
    var info: String = "Exercise Info "

    companion object {
        fun newInstance(): Exercise = Exercise()
    }
}

@Entity(tableName = "exercises")
data class ExerciseEntity(
    @PrimaryKey var exerciseId: UUID = UUID.randomUUID(),
    var exerciseName: String = ""
) {
    companion object {
        fun toExercise(entity: ExerciseEntity): Exercise {
            return Exercise.newInstance().apply {
                id = entity.exerciseId
                name = entity.exerciseName
            }
        }
        fun fromExercise(exercise: Exercise): ExerciseEntity {
            return ExerciseEntity().apply {
                exerciseId = exercise.id
                exerciseName = exercise.name
            }
        }
    }
}