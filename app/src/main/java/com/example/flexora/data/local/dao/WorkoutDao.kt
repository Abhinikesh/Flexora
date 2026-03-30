package com.example.flexora.data.local.dao

import androidx.room.*
import com.example.flexora.domain.model.Workout
import kotlinx.coroutines.flow.Flow

@Dao
interface WorkoutDao {
    @Query("SELECT * FROM workouts ORDER BY date DESC")
    fun getAllWorkouts(): Flow<List<Workout>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWorkout(workout: Workout)

    @Delete
    suspend fun deleteWorkout(workout: Workout)

    @Query("SELECT * FROM workouts WHERE date >= :startOfDay")
    fun getTodayWorkouts(startOfDay: Long): Flow<List<Workout>>
}
