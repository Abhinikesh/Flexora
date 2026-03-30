package com.example.flexora.domain.repository

import com.example.flexora.domain.model.Workout
import kotlinx.coroutines.flow.Flow

interface WorkoutRepository {
    fun getAllWorkouts(): Flow<List<Workout>>
    fun getTodayWorkouts(): Flow<List<Workout>>
    suspend fun insertWorkout(workout: Workout)
    suspend fun updateWorkout(workout: Workout)
    suspend fun deleteWorkout(workout: Workout)
    suspend fun getLatestWorkoutForExercise(name: String): Workout?
    suspend fun getWorkoutById(id: Int): Workout?
}
