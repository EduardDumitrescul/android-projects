package com.example.workoutcompanion2.database

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.room.Room
import com.example.workoutcompanion2.exercise.Exercise
import com.example.workoutcompanion2.exercise.ExerciseEntity
import java.lang.IllegalStateException
import java.util.*

private const val DATABASE_NAME = "workout-database"

class AppRepository private constructor(context: Context) {
    private val database: AppDatabase = Room.databaseBuilder(
        context.applicationContext,
        AppDatabase::class.java,
        DATABASE_NAME
    ).build()

    private val appDao = database.appDao()

    fun getExerciseList(): LiveData<List<Exercise>> {
        return Transformations.map(appDao.getExercises()) { exerciseList ->
            exerciseList.map{ entity ->
                ExerciseEntity.toExercise(entity)
            }
        }
    }

    fun getExercise(id: UUID): LiveData<Exercise> {
        return Transformations.map(appDao.getExercise(id)) { entity ->
            ExerciseEntity.toExercise(entity)
        }
    }

    companion object {
        private var INSTANCE: AppRepository? = null

        fun initialize(context: Context) {
            if(INSTANCE == null) {
                INSTANCE = AppRepository(context)
            }
        }

        fun get(): AppRepository {
            return INSTANCE ?:
            throw IllegalStateException("AppRepository must be initialized")
        }
    }
}