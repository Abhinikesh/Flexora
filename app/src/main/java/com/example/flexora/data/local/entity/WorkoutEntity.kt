package com.example.flexora.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "workouts")
data class WorkoutEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val sets: Int,
    val reps: Int,
    val completedReps: Int = 0,
    val completedSets: Int = 0,
    val duration: Int,
    val notes: String = "",
    val timestamp: Long,
    val isCompleted: Boolean = false
)
