package com.example.flexora.data.mapper

import com.example.flexora.data.local.entity.WorkoutEntity
import com.example.flexora.domain.model.Workout

fun WorkoutEntity.toWorkout(): Workout {
    return Workout(
        id = id,
        exerciseName = name,
        sets = sets,
        reps = reps,
        completedReps = completedReps,
        completedSets = completedSets,
        durationMinutes = duration,
        notes = notes,
        date = timestamp,
        isCompleted = isCompleted
    )
}

fun Workout.toEntity(): WorkoutEntity {
    return WorkoutEntity(
        id = id,
        name = exerciseName,
        sets = sets,
        reps = reps,
        completedReps = completedReps,
        completedSets = completedSets,
        duration = durationMinutes,
        notes = notes,
        timestamp = date,
        isCompleted = isCompleted
    )
}
