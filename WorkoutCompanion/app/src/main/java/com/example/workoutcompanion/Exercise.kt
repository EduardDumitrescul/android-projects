package com.example.workoutcompanion

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*


class Exercise() {
    var exerciseId: UUID = UUID.randomUUID()
    var name: String = ""
    var equipmentId: UUID = UUID.randomUUID()
    var equipment: String = ""
    var primaryMuscle: Muscle = Muscle()

    companion object {
        fun newInstance(
            id_exercise: UUID,
            name: String,
            equipmentId: UUID,
            primaryMuscleId: UUID
        ): Exercise {

            return Exercise().apply {
                this.exerciseId = id_exercise
                this.equipmentId = equipmentId
                this.name = name
            }
        }
    }
}

@Entity (tableName = "exercises")
class ExerciseEntity(
    @PrimaryKey val id_exercise: UUID = UUID.randomUUID(),
    var name: String = "",
    var equipmentId: UUID,
    var primaryMuscleId: UUID
) {
    companion object {
        fun fromExercise(exercise: Exercise): ExerciseEntity {
            return ExerciseEntity(exercise.exerciseId, exercise.name, exercise.equipmentId, exercise.primaryMuscle.muscleId)
        }

        fun toExercise(exerciseEntity: ExerciseEntity?): Exercise? {
            if(exerciseEntity == null)
                return null
            return Exercise.newInstance(exerciseEntity.id_exercise, exerciseEntity.name, exerciseEntity.equipmentId, exerciseEntity.primaryMuscleId)
        }
    }
}