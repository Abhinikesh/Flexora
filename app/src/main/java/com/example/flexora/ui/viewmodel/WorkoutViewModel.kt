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
import javax.inject.Inject

@HiltViewModel
class WorkoutViewModel @Inject constructor(
    private val repository: WorkoutRepository
) : ViewModel() {

    val allWorkouts: StateFlow<List<Workout>> = repository.getAllWorkouts()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val todayWorkouts: StateFlow<List<Workout>> = repository.getTodayWorkouts()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    private val _exerciseName = mutableStateOf("")
    val exerciseName: State<String> = _exerciseName

    private val _sets = mutableStateOf("")
    val sets: State<String> = _sets

    private val _reps = mutableStateOf("")
    val reps: State<String> = _reps

    private val _duration = mutableStateOf("")
    val duration: State<String> = _duration

    private val _suggestion = mutableStateOf<String?>(null)
    val suggestion: State<String?> = _suggestion

    fun onExerciseNameChange(value: String) { 
        _exerciseName.value = value 
        updateSuggestion(value)
    }
    fun onSetsChange(value: String) { _sets.value = value }
    fun onRepsChange(value: String) { _reps.value = value }
    fun onDurationChange(value: String) { _duration.value = value }

    private fun updateSuggestion(name: String) {
        viewModelScope.launch {
            val latest = repository.getLatestWorkoutForExercise(name)
            latest?.let {
                val suggestedReps = (it.reps * 1.1).toInt().coerceAtLeast(it.reps + 1)
                _suggestion.value = "Last time you did ${it.reps} reps. Try ${suggestedReps} today!"
            } ?: run {
                _suggestion.value = null
            }
        }
    }

    fun saveWorkout(onComplete: () -> Unit) {
        viewModelScope.launch {
            val workout = Workout(
                exerciseName = _exerciseName.value,
                sets = _sets.value.toIntOrNull() ?: 0,
                reps = _reps.value.toIntOrNull() ?: 0,
                durationMinutes = _duration.value.toIntOrNull() ?: 0
            )
            repository.insertWorkout(workout)
            onComplete()
        }
    }

    fun updateProgress(workout: Workout, completedReps: Int, completedSets: Int) {
        viewModelScope.launch {
            val updated = workout.copy(
                completedReps = completedReps,
                completedSets = completedSets,
                isCompleted = completedSets >= workout.sets
            )
            repository.updateWorkout(updated)
        }
    }
}
