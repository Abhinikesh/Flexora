package com.example.flexora.ui.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flexora.domain.model.Workout
import com.example.flexora.domain.repository.WorkoutRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.Calendar
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class WorkoutViewModel @Inject constructor(
    private val repository: WorkoutRepository
) : ViewModel() {

    // 1. Single Source of Truth: Exposing Flow from Room
    val allWorkouts: StateFlow<List<Workout>> = repository.getAllWorkouts()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val todayWorkouts: StateFlow<List<Workout>> = repository.getTodayWorkouts()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // 2. Real-Time Dashboard Stats (Automatically updated when allWorkouts changes)
    val totalWorkoutsCount = allWorkouts.map { it.size }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0)

    val totalTimeSpent = allWorkouts.map { list ->
        list.sumOf { it.durationMinutes }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0)

    val currentStreak = allWorkouts.map { calculateStreak(it) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0)

    val totalReps = allWorkouts.map { list ->
        list.sumOf { it.completedReps }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0)

    val averageDuration = allWorkouts.map { list ->
        if (list.isEmpty()) 0 else list.sumOf { it.durationMinutes } / list.size
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0)

    // 3. Dynamic Smart Suggestion for Dashboard
    val dashboardSuggestion = allWorkouts.map { list ->
        if (list.isEmpty()) {
            "Start your first workout to get smart suggestions! ⚡"
        } else {
            val latest = list.first() // List is sorted by date DESC
            val suggestedReps = (latest.reps * 1.1).toInt().coerceAtLeast(latest.reps + 1)
            "Great job on ${latest.exerciseName}! Try aiming for $suggestedReps reps in your next session. 📈"
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), "Loading your next goal...")

    // Form states
    private val _exerciseName = mutableStateOf("")
    val exerciseName: State<String> = _exerciseName

    private val _sets = mutableStateOf("")
    val sets: State<String> = _sets

    private val _reps = mutableStateOf("")
    val reps: State<String> = _reps

    private val _duration = mutableStateOf("")
    val duration: State<String> = _duration

    fun onExerciseNameChange(value: String) { _exerciseName.value = value }
    fun onSetsChange(value: String) { _sets.value = value }
    fun onRepsChange(value: String) { _reps.value = value }
    fun onDurationChange(value: String) { _duration.value = value }

    fun addWorkout(workout: Workout) {
        viewModelScope.launch {
            repository.insertWorkout(workout)
        }
    }

    fun saveWorkout(onSuccess: () -> Unit) {
        val name = _exerciseName.value
        val setsCount = _sets.value.toIntOrNull() ?: 0
        val repsCount = _reps.value.toIntOrNull() ?: 0
        val durationMin = _duration.value.toIntOrNull() ?: 0

        if (name.isNotBlank() && setsCount > 0 && repsCount > 0) {
            val workout = Workout(
                exerciseName = name,
                sets = setsCount,
                reps = repsCount,
                durationMinutes = durationMin
            )
            viewModelScope.launch {
                repository.insertWorkout(workout)
                onSuccess()
                // Clear form
                _exerciseName.value = ""
                _sets.value = ""
                _reps.value = ""
                _duration.value = ""
            }
        }
    }

    fun deleteWorkout(workout: Workout) {
        viewModelScope.launch {
            repository.deleteWorkout(workout)
        }
    }

    fun updateWorkout(workout: Workout) {
        viewModelScope.launch {
            repository.updateWorkout(workout)
        }
    }

    fun updateProgress(workout: Workout, reps: Int, sets: Int) {
        viewModelScope.launch {
            val updatedWorkout = workout.copy(
                completedReps = reps,
                completedSets = sets,
                isCompleted = reps >= workout.reps && sets >= workout.sets
            )
            repository.updateWorkout(updatedWorkout)
        }
    }

    private fun calculateStreak(workouts: List<Workout>): Int {
        if (workouts.isEmpty()) return 0
        
        val dates = workouts.map { 
            Calendar.getInstance().apply { 
                timeInMillis = it.date 
                set(Calendar.HOUR_OF_DAY, 0)
                set(Calendar.MINUTE, 0)
                set(Calendar.SECOND, 0)
                set(Calendar.MILLISECOND, 0)
            }.timeInMillis 
        }.distinct().sortedDescending()

        var streak = 0
        val today = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }.timeInMillis

        var currentDay = today
        
        // If the most recent workout is not today or yesterday, streak might be broken
        if (dates.isEmpty() || (today - dates[0] > TimeUnit.DAYS.toMillis(1))) {
            return 0
        }

        for (date in dates) {
            if (date == currentDay || date == currentDay - TimeUnit.DAYS.toMillis(1)) {
                streak++
                currentDay = date
            } else {
                break
            }
        }
        return streak
    }
}
