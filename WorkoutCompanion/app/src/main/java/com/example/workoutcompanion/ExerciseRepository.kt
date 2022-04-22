package com.example.workoutcompanion

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.room.Room
import com.example.workoutcompanion.database.ExerciseDatabase
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

    fun getExercises(): LiveData<List<Exercise>> {
        val entities = exerciseDao.getExercises()
        return Transformations.map(entities) { entityList ->
            entityList.map {
                (ExerciseEntity.toExercise(it) as Exercise).apply {
                    this.primaryMuscle = getMuscle(it.primaryMuscleId).value!!
                }
            }
        }
    }

    fun getExercise(id: UUID): LiveData<Exercise?> {
        return Transformations.map(exerciseDao.getExercise(id)) {entity ->
            ExerciseEntity.toExercise(entity)
        }
    }

    fun saveExercise(exercise: Exercise) {
        Log.d(TAG, "saveExercise()")
        val exerciseEntity = ExerciseEntity.fromExercise(exercise)
        executor.execute {
            exerciseDao.insertExercise(exerciseEntity)
        }
    }

    fun getMuscles(): LiveData<List<Muscle>> {
        val entities = exerciseDao.getMuscles()
        return Transformations.map(entities) {  entityList ->
            entityList.map{
                MuscleEntity.toMuscle(it) as Muscle
            }
        }
    }

    fun getMuscle(id: UUID): LiveData<Muscle?> {
        return Transformations.map(exerciseDao.getMuscle(id)) {
            it.let {MuscleEntity.toMuscle(it)}
        }
    }

    fun saveMuscle(muscle: Muscle) {
        exerciseDao.insertMuscle(MuscleEntity.fromMuscle(muscle))
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