package com.example.workoutcompanion.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.workoutcompanion.Exercise
import java.util.*

@Dao
interface ExerciseDao {

    @Query("SELECT * FROM exercise")
    fun getExercises(): LiveData<List<Exercise>>

    @Query("SELECT * FROM exercise WHERE id_exercise=(:id)")
    fun getExercise(id: UUID): LiveData<Exercise?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertExercise(exercise: Exercise)
}