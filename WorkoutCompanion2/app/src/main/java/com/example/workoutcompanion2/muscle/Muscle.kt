package com.example.workoutcompanion2.muscle

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*
import kotlin.random.Random

class Muscle {
    var id: UUID = UUID.randomUUID()
    var name: String = "name"

    companion object {
        fun newInstance(): Muscle = Muscle()
    }
}

@Entity(tableName = "muscles")
data class MuscleEntity(
    @PrimaryKey val muscleId: UUID = UUID.randomUUID(),
    val muscleName: String = ""
) {
    companion object {
        fun toMuscle(entity: MuscleEntity): Muscle {
            return Muscle.newInstance().apply {
                id = entity.muscleId
                name = entity.muscleName
            }
        }
        fun fromMuscle(muscle: Muscle): MuscleEntity {
            return MuscleEntity(
                muscleId = muscle.id,
                muscleName = muscle.name
            )
        }
    }
}