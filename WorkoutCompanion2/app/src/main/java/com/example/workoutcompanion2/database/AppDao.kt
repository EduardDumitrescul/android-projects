package com.example.workoutcompanion2.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.workoutcompanion2.exercise.ExerciseEntity
import java.util.*

@Dao
interface AppDao {
    @Query("SELECT * FROM exercises")
    fun getExercises(): LiveData<List<ExerciseEntity>>

    @Query("SELECT * FROM exercises WHERE exerciseId=(:id)")
    fun getExercise(id: UUID): LiveData<ExerciseEntity>

    @Insert
    fun insertExercise(entity: ExerciseEntity)
}