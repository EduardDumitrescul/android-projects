package com.example.workoutcompanion

import androidx.annotation.InspectableProperty
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
class Exercise(@PrimaryKey
               val id_exercise: UUID = UUID.randomUUID(),
               var name: String = "",
               var equipment: String = "") {

}