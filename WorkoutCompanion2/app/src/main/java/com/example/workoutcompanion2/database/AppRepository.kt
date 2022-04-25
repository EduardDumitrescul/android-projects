package com.example.workoutcompanion2.database

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.workoutcompanion2.exercise.Exercise
import com.example.workoutcompanion2.exercise.ExerciseEntity
import com.example.workoutcompanion2.muscle.Muscle
import com.example.workoutcompanion2.muscle.MuscleEntity
import java.lang.IllegalStateException
import java.util.*
import java.util.concurrent.Executors
import javax.security.auth.callback.Callback

private const val TAG = "app-repository"
private const val DATABASE_NAME = "workout-database"

private val muscleList: List<String> = listOf("Chest", "Back", "Biceps", "Triceps", "Abdominals", "Shoulders", "Quadriceps", "Hamstrings", "Calves", "Glutes")

class AppRepository private constructor(context: Context) {
    private val database: AppDatabase = Room.databaseBuilder(
        context.applicationContext,
        AppDatabase::class.java,
        DATABASE_NAME
    ).addCallback(object: RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            muscleList.forEach { muscle ->
                insertMuscle(Muscle.newInstance(muscle))
            }
        }
    }).build()

    private val executor = Executors.newSingleThreadExecutor()

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

    fun insertExercise(exercise: Exercise) {
        executor.execute {
            appDao.insertExercise(ExerciseEntity.fromExercise(exercise))
        }
    }

    fun updateExercise(exercise: Exercise) {
        executor.execute {
            appDao.updateExercise(ExerciseEntity.fromExercise(exercise))
        }
    }
    
    fun deleteExercise(exercise: Exercise) {
        appDao.deleteExercise(ExerciseEntity.fromExercise(exercise))
    }

    fun deleteExerciseById(id: UUID) {
        executor.execute {
            appDao.deleteExerciseById(id)
        }
    }

    fun getMuscleList(): LiveData<List<Muscle>> {
        return Transformations.map(appDao.getMuscles()) { muscleList ->
            muscleList.map { entity ->
                MuscleEntity.toMuscle(entity)
            }
        }
    }

    fun getMuscle(id: UUID): LiveData<Muscle> {
        return Transformations.map(appDao.getMuscle(id)) { entity ->
            MuscleEntity.toMuscle(entity)
        }
    }

    fun insertMuscle(muscle: Muscle) {
        executor.execute {
            appDao.insertMuscle(MuscleEntity.fromMuscle(muscle))
        }
    }

    fun updateMuscle(muscle: Muscle) {
        executor.execute {
            appDao.updateMuscle(MuscleEntity.fromMuscle(muscle))
        }
    }

    fun deleteMuscleById(id: UUID) {
        executor.execute {
            appDao.deleteMuscleById(id)
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