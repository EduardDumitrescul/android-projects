package com.example.workoutcompanion

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.Room
import com.example.workoutcompanion.database.ExerciseDatabase
import com.example.workoutcompanion.database.ExerciseEntity
import java.lang.IllegalStateException
import java.util.*
import java.util.concurrent.Executors

private const val TAG = "ExerciseRepository"
private const val DATABASE_NAME = "exercise-database"

class ExerciseRepository private constructor(context: Context) {

    private val database: ExerciseDatabase = Room.databaseBuilder(
        context.applicationContext,
        ExerciseDatabase::class.java,
        DATABASE_NAME
    ).build()

    private val exerciseDao = database.exerciseDao()
    private val executor = Executors.newSingleThreadExecutor()

    fun getExercises(): LiveData<List<Exercise>> = exerciseDao.getExercises()

    fun getExercise(id: UUID): LiveData<Exercise?> = exerciseDao.getExercise(id)
    fun saveExercise(exercise: Exercise) {
        Log.d(TAG, "saveExercise()")
        val exerciseEntity = ExerciseEntity.fromExercise(exercise)
        executor.execute {
            exerciseDao.insertExercise(exerciseEntity)
        }
    }

    companion object {
        private var INSTANCE: ExerciseRepository? = null

        fun initialize(context: Context) {
            if(INSTANCE == null) {
                INSTANCE = ExerciseRepository(context)
            }
        }

        fun get(): ExerciseRepository {
            return INSTANCE ?:
            throw IllegalStateException("ExerciseRepository must be initialized")
        }
    }
}