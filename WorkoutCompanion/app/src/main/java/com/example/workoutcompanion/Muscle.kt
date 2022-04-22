package com.example.workoutcompanion

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

class Muscle() {
    var muscleId: UUID = UUID.randomUUID()
    var name: String = ""
    companion object {
        fun newInstance(
            muscleId: UUID,
            name: String
        ):Muscle {
             return Muscle().apply {
                 this.muscleId = muscleId
                 this.name = name
             }
        }
    }
}

@Entity (tableName = "muscles")
class MuscleEntity(
    @PrimaryKey val id_muscle: UUID = UUID.randomUUID(),
    var name: String = ""
) {
    companion object {
        fun fromMuscle(muscle: Muscle): MuscleEntity {
            return MuscleEntity(muscle.muscleId, muscle.name)
        }

        fun toMuscle(entity: MuscleEntity?): Muscle? {
            return entity?.let {
                Muscle.newInstance(it.id_muscle, it.name)
            }
        }
    }
}