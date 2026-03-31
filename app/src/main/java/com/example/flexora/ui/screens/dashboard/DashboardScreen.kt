package com.example.flexora.ui.screens.dashboard

import androidx.compose.animation.*
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.flexora.domain.model.Workout
import com.example.flexora.ui.components.PremiumCard
import com.example.flexora.ui.components.StatCard
import com.example.flexora.ui.theme.PrimaryPurple
import com.example.flexora.ui.theme.SuccessGreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    onNavigateToAddWorkout: () -> Unit,
    todayWorkouts: List<Workout>,
    suggestion: String?,
    totalWorkouts: Int,
    timeSpent: Int,
    streak: Int,
    onUpdateProgress: (Workout, Int, Int) -> Unit
) {
    Scaffold(
        topBar = {
            LargeTopAppBar(
                title = {
                    Column {
                        Text(
                            "Flexora",
                            style = MaterialTheme.typography.headlineMedium,
                            fontWeight = FontWeight.Bold,
                            color = PrimaryPurple
                        )
                        Text(
                            "Welcome back, Alex!",
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                        )
                    }
                },
                colors = TopAppBarDefaults.largeTopAppBarColors(
                    containerColor = Color.Transparent,
                    scrolledContainerColor = MaterialTheme.colorScheme.background
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onNavigateToAddWorkout,
                containerColor = PrimaryPurple,
                contentColor = Color.White,
                shape = MaterialTheme.shapes.large
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add Workout")
            }
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentPadding = PaddingValues(20.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            // Smart Suggestion with Animation
            item {
                AnimatedVisibility(
                    visible = suggestion != null,
                    enter = expandVertically() + fadeIn(),
                    exit = shrinkVertically() + fadeOut()
                ) {
                    suggestion?.let {
                        PremiumCard(modifier = Modifier.fillMaxWidth()) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Default.Lightbulb, null, tint = Color(0xFFFFC107))
                                Spacer(Modifier.width(12.dp))
                                Text(it, style = MaterialTheme.typography.bodyMedium)
                            }
                        }
                    }
                }
            }

            // Real-time Stats Cards
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    StatCard(
                        label = "Total Workouts",
                        value = totalWorkouts.toString(),
                        icon = Icons.Default.FitnessCenter,
                        modifier = Modifier.weight(1f)
                    )
                    StatCard(
                        label = "Streak",
                        value = "$streak Days",
                        icon = Icons.Default.LocalFireDepartment,
                        modifier = Modifier.weight(1f)
                    )
                }
            }

            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    StatCard(
                        label = "Time Spent",
                        value = "${timeSpent}m",
                        icon = Icons.Default.Timer,
                        modifier = Modifier.weight(1f)
                    )
                    StatCard(
                        label = "Active Now",
                        value = todayWorkouts.size.toString(),
                        icon = Icons.Default.Check,
                        modifier = Modifier.weight(1f)
                    )
                }
            }

            item {
                Text(
                    "Today's Activity",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
            }

            items(todayWorkouts, key = { it.id }) { workout ->
                AnimatedContent(
                    targetState = workout,
                    transitionSpec = {
                        fadeIn() togetherWith fadeOut()
                    }, label = ""
                ) { targetWorkout ->
                    TrackingWorkoutCard(targetWorkout, onUpdateProgress)
                }
            }
            
            if (todayWorkouts.isEmpty()) {
                item {
                    Text(
                        "No workouts yet. Start your journey today!",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                        modifier = Modifier.padding(top = 16.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun TrackingWorkoutCard(workout: Workout, onUpdateProgress: (Workout, Int, Int) -> Unit) {
    PremiumCard {
        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        workout.exerciseName,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        "${workout.sets} sets • ${workout.reps} reps",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                    )
                }
                if (workout.isCompleted) {
                    Icon(Icons.Default.Check, null, tint = SuccessGreen)
                }
            }

            // Progress Bar with smooth animation
            val progress by animateFloatAsState(
                targetValue = if (workout.sets > 0) workout.completedSets.toFloat() / workout.sets else 0f,
                label = "workout_progress"
            )

            LinearProgressIndicator(
                progress = { progress },
                modifier = Modifier.fillMaxWidth().height(8.dp),
                color = PrimaryPurple,
                trackColor = PrimaryPurple.copy(alpha = 0.1f)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "Sets: ${workout.completedSets} / ${workout.sets}",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Medium
                )
                
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    FilledTonalButton(
                        onClick = { 
                            if (workout.completedSets < workout.sets) {
                                onUpdateProgress(workout, workout.completedReps + workout.reps, workout.completedSets + 1)
                            }
                        },
                        enabled = !workout.isCompleted,
                        shape = MaterialTheme.shapes.medium,
                        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 4.dp)
                    ) {
                        Text("+ Set")
                    }
                }
            }
        }
    }
}
