package com.example.flexora.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.flexora.data.local.dao.WorkoutDao
import com.example.flexora.domain.model.Workout

@Database(entities = [Workout::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun workoutDao(): WorkoutDao
}
