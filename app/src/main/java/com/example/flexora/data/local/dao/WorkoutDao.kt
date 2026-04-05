package com.example.flexora.data.local.dao

import androidx.room.*
import com.example.flexora.data.local.entity.WorkoutEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface WorkoutDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWorkout(workout: WorkoutEntity)

    @Query("SELECT * FROM workouts ORDER BY timestamp DESC")
    fun getAllWorkouts(): Flow<List<WorkoutEntity>>

    @Delete
    suspend fun deleteWorkout(workout: WorkoutEntity)

    @Update
    suspend fun updateWorkout(workout: WorkoutEntity)

    @Query("SELECT * FROM workouts WHERE id = :id")
    suspend fun getWorkoutById(id: Int): WorkoutEntity?

    @Query("SELECT * FROM workouts WHERE timestamp >= :timestamp ORDER BY timestamp DESC")
    fun getTodayWorkouts(timestamp: Long): Flow<List<WorkoutEntity>>

    @Query("SELECT * FROM workouts WHERE name = :name ORDER BY timestamp DESC LIMIT 1")
    suspend fun getLatestWorkoutForExercise(name: String): WorkoutEntity?
}
