package com.example.workoutcompanion.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.workoutcompanion.Exercise
import com.example.workoutcompanion.ExerciseEntity
import com.example.workoutcompanion.Muscle
import com.example.workoutcompanion.MuscleEntity
import java.util.*

@Dao
interface ExerciseDao {

    @Query("SELECT * FROM exercises")
    fun getExercises(): LiveData<List<ExerciseEntity>>

    @Query("SELECT * FROM exercises WHERE id_exercise=(:id)")
    fun getExercise(id: UUID): LiveData<ExerciseEntity?>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertExercise(exerciseEntity: ExerciseEntity)


    @Query("SELECT * FROM muscles")
    fun getMuscles(): LiveData<List<MuscleEntity>>

    @Query("SELECT * FROM muscles WHERE id_muscle=(:id)")
    fun getMuscle(id: UUID): LiveData<MuscleEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertMuscle(muscleEntity: MuscleEntity)
}