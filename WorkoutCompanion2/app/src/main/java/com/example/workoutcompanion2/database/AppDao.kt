package com.example.workoutcompanion2.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.workoutcompanion2.exercise.ExerciseEntity
import com.example.workoutcompanion2.muscle.MuscleEntity
import java.util.*

@Dao
interface AppDao {
    @Query("SELECT * FROM exercises")
    fun getExercises(): LiveData<List<ExerciseEntity>>

    @Query("SELECT * FROM exercises WHERE exerciseId=(:id)")
    fun getExercise(id: UUID): LiveData<ExerciseEntity>

    @Insert
    fun insertExercise(entity: ExerciseEntity)

    @Update
    fun updateExercise(entity: ExerciseEntity)

    @Delete
    fun deleteExercise(entity: ExerciseEntity)

    @Query("DELETE FROM exercises WHERE exerciseId=(:id)")
    fun deleteExerciseById(id: UUID)


    @Query("SELECT * FROM muscles")
    fun getMuscles(): LiveData<List<MuscleEntity>>

    @Query("SELECT * FROM muscles WHERE muscleId=(:id)")
    fun getMuscle(id: UUID): LiveData<MuscleEntity>

    @Insert
    fun insertMuscle(entity: MuscleEntity)

    @Update
    fun updateMuscle(entity: MuscleEntity)

    @Query("DELETE FROM muscles WHERE muscleId=(:id)")
    fun deleteMuscleById(id: UUID)
}