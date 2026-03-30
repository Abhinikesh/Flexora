package com.example.flexora.ui.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flexora.domain.model.Workout
import com.example.flexora.domain.repository.WorkoutRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
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

    fun onExerciseNameChange(value: String) { _exerciseName.value = value }
    fun onSetsChange(value: String) { _sets.value = value }
    fun onRepsChange(value: String) { _reps.value = value }
    fun onDurationChange(value: String) { _duration.value = value }

    fun saveWorkout(onComplete: () -> Unit) {
        viewModelScope.launch {
            val workout = Workout(
                exerciseName = _exerciseName.value,
                sets = _sets.value.toIntOrNull() ?: 0,
                reps = _reps.value.toIntOrNull() ?: 0,
                durationMinutes = _duration.value.toIntOrNull() ?: 0,
                notes = ""
            )
            repository.insertWorkout(workout)
            onComplete()
        }
    }
}
