package com.example.flexora.ui.screens.dashboard

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.flexora.ui.components.PremiumCard
import com.example.flexora.ui.components.StatCard
import com.example.flexora.ui.theme.PrimaryPurple

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    onNavigateToAddWorkout: () -> Unit
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
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    StatCard(
                        label = "Workouts",
                        value = "12",
                        icon = Icons.Default.FitnessCenter,
                        modifier = Modifier.weight(1f)
                    )
                    StatCard(
                        label = "Streak",
                        value = "5 Days",
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
                        value = "8.5h",
                        icon = Icons.Default.Timer,
                        modifier = Modifier.weight(1f)
                    )
                    StatCard(
                        label = "History",
                        value = "24",
                        icon = Icons.Default.History,
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

            val todayWorkouts = listOf(
                WorkoutItemData("Morning Yoga", "45 min", "Low Intensity"),
                WorkoutItemData("Chest & Triceps", "1h 20m", "High Intensity"),
                WorkoutItemData("Evening Walk", "30 min", "Medium Intensity")
            )

            items(todayWorkouts) { workout ->
                WorkoutCard(workout)
            }
        }
    }
}

data class WorkoutItemData(val name: String, val duration: String, val intensity: String)

@Composable
fun WorkoutCard(workout: WorkoutItemData) {
    PremiumCard {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    workout.name,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    "${workout.duration} • ${workout.intensity}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                )
            }
            Icon(
                imageVector = Icons.Default.FitnessCenter,
                contentDescription = null,
                tint = PrimaryPurple
            )
        }
    }
}
