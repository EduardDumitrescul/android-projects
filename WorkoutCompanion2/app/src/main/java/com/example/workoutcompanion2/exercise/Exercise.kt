package com.example.workoutcompanion2.exercise

import android.util.Log
import androidx.annotation.Nullable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.*
import com.example.workoutcompanion2.database.AppRepository
import com.example.workoutcompanion2.muscle.Muscle
import com.example.workoutcompanion2.muscle.MuscleEntity
import java.util.*

private  val TAG = "Exercise.kt"

class Exercise {
    var id: UUID = UUID.randomUUID()
    var name: String = "Exercise Name "
    var info: String = "Exercise Info "
    var primaryMuscle: Muscle? = null
    var secondaryMuscles: List<Muscle> = listOf()

    companion object {
        fun newInstance(): Exercise = Exercise()
    }
}

@Entity(tableName = "exercises")
data class ExerciseEntity(
    @PrimaryKey var exerciseId: UUID = UUID.randomUUID(),
    var exerciseName: String = "",
    var primaryMuscleId: UUID?,
) {
    companion object {
        private val repository: AppRepository = AppRepository.get()
        fun toExercise(entity: ExerciseEntity): Exercise {
            return Exercise.newInstance().apply {
                id = entity.exerciseId
                name = entity.exerciseName
            }
        }
        fun fromExercise(exercise: Exercise): ExerciseEntity {
            Log.d(TAG, exercise.primaryMuscle?.id.toString())
            return ExerciseEntity(
                exerciseId = exercise.id,
                exerciseName = exercise.name,
                primaryMuscleId = exercise.primaryMuscle?.id
            )
        }
    }
}

@Entity(primaryKeys = ["exerciseId", "muscleId"], tableName = "exerciseMuscleCrossRef")
data class ExerciseMuscleCrossRef (
    val exerciseId: UUID,
    val muscleId: UUID
)

data class ExerciseWithSecondaryMuscles(
    @Embedded val exerciseEntity: ExerciseEntity,
    @Relation(
        parentColumn = "exerciseId",
        entityColumn = "muscleId",
        associateBy = Junction(ExerciseWithSecondaryMuscles::class)
    )
    val secondaryMuscleList: List<Muscle>
)

data class ExerciseWithMusclesEntity(
    @Embedded val exerciseEntity: ExerciseEntity,
    @Relation(
        parentColumn = "primaryMuscleId",
        entityColumn = "muscleId"
    )
    val primaryMuscleEntity: MuscleEntity?,
    @Relation(
        parentColumn = "exerciseId",
        entityColumn = "muscleId",
        associateBy = Junction(ExerciseMuscleCrossRef::class)
    )
    val secondaryMuscleList: List<MuscleEntity>
) {
    companion object {
        fun toExercise(entity: ExerciseWithMusclesEntity): Exercise {
            return ExerciseEntity.toExercise(entity.exerciseEntity).apply {
                this.primaryMuscle = entity.primaryMuscleEntity?.let { MuscleEntity.toMuscle(it) }
                this.secondaryMuscles = entity.secondaryMuscleList.map {
                    MuscleEntity.toMuscle(it)
                }
            }
        }
        fun fromExercise(exercise: Exercise): ExerciseWithMusclesEntity {
            return ExerciseWithMusclesEntity(
                exerciseEntity = ExerciseEntity.fromExercise(exercise),
                primaryMuscleEntity = exercise.primaryMuscle?.let { MuscleEntity.fromMuscle(it) },
                secondaryMuscleList = exercise.secondaryMuscles.map {
                    MuscleEntity.fromMuscle(it)
                }
            )
        }

    }
}