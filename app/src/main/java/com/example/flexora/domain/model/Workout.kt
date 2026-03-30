package com.example.flexora.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "workouts")
data class Workout(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val exerciseName: String,
    val sets: Int,
    val reps: Int,
    val durationMinutes: Int,
    val notes: String,
    val date: Long = System.currentTimeMillis(),
    val isCompleted: Boolean = false
)
