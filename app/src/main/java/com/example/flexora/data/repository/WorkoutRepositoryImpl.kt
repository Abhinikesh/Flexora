package com.example.flexora.data.repository

import com.example.flexora.data.local.dao.WorkoutDao
import com.example.flexora.data.mapper.toEntity
import com.example.flexora.data.mapper.toWorkout
import com.example.flexora.domain.model.Workout
import com.example.flexora.domain.repository.WorkoutRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.*
import javax.inject.Inject

class WorkoutRepositoryImpl @Inject constructor(
    private val dao: WorkoutDao
) : WorkoutRepository {
    override fun getAllWorkouts(): Flow<List<Workout>> {
        return dao.getAllWorkouts().map { entities ->
            entities.map { it.toWorkout() }
        }
    }

    override fun getTodayWorkouts(): Flow<List<Workout>> {
        val calendar = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }
        return dao.getTodayWorkouts(calendar.timeInMillis).map { entities ->
            entities.map { it.toWorkout() }
        }
    }

    override suspend fun insertWorkout(workout: Workout) {
        dao.insertWorkout(workout.toEntity())
    }

    override suspend fun updateWorkout(workout: Workout) {
        dao.updateWorkout(workout.toEntity())
    }

    override suspend fun deleteWorkout(workout: Workout) {
        dao.deleteWorkout(workout.toEntity())
    }

    override suspend fun getLatestWorkoutForExercise(name: String): Workout? {
        return dao.getLatestWorkoutForExercise(name)?.toWorkout()
    }

    override suspend fun getWorkoutById(id: Int): Workout? {
        return dao.getWorkoutById(id)?.toWorkout()
    }
}
