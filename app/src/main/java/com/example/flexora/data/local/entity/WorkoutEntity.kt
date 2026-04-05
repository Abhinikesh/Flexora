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
    val duration: String,
    val timestamp: Long
)
