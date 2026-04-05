package com.example.flexora.data.mapper

import com.example.flexora.data.local.entity.WorkoutEntity
import com.example.flexora.domain.model.Workout

fun WorkoutEntity.toWorkout(): Workout {
    return Workout(
        id = id,
        exerciseName = name,
        sets = sets,
        reps = reps,
        durationMinutes = duration.toIntOrNull() ?: 0,
        date = timestamp
    )
}

fun Workout.toEntity(): WorkoutEntity {
    return WorkoutEntity(
        id = id,
        name = exerciseName,
        sets = sets,
        reps = reps,
        duration = durationMinutes.toString(),
        timestamp = date
    )
}
