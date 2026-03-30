package com.example.flexora.data.repository

import com.example.flexora.data.local.dao.WorkoutDao
import com.example.flexora.domain.model.Workout
import com.example.flexora.domain.repository.WorkoutRepository
import kotlinx.coroutines.flow.Flow
import java.util.*
import javax.inject.Inject

class WorkoutRepositoryImpl @Inject constructor(
    private val dao: WorkoutDao
) : WorkoutRepository {
    override fun getAllWorkouts(): Flow<List<Workout>> = dao.getAllWorkouts()

    override fun getTodayWorkouts(): Flow<List<Workout>> {
        val calendar = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }
        return dao.getTodayWorkouts(calendar.timeInMillis)
    }

    override suspend fun insertWorkout(workout: Workout) = dao.insertWorkout(workout)
    override suspend fun updateWorkout(workout: Workout) = dao.updateWorkout(workout)
    override suspend fun deleteWorkout(workout: Workout) = dao.deleteWorkout(workout)
    override suspend fun getLatestWorkoutForExercise(name: String) = dao.getLatestWorkoutForExercise(name)
    override suspend fun getWorkoutById(id: Int) = dao.getWorkoutById(id)
}
