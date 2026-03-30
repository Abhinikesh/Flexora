package com.example.flexora.ui.screens.workout

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Repeat
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.flexora.ui.components.GradientButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddWorkoutScreen(
    onWorkoutSaved: () -> Unit,
    onNavigateBack: () -> Unit
) {
    var exerciseName by remember { mutableStateOf("") }
    var sets by remember { mutableStateOf("") }
    var reps by remember { mutableStateOf("") }
    var duration by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Add Workout", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(20.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            OutlinedTextField(
                value = exerciseName,
                onValueChange = { exerciseName = it },
                label = { Text("Exercise Name") },
                placeholder = { Text("e.g. Bench Press") },
                modifier = Modifier.fillMaxWidth(),
                leadingIcon = { Icon(Icons.Default.FitnessCenter, null) },
                shape = MaterialTheme.shapes.large
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                OutlinedTextField(
                    value = sets,
                    onValueChange = { sets = it },
                    label = { Text("Sets") },
                    modifier = Modifier.weight(1f),
                    leadingIcon = { Icon(Icons.Default.History, null) },
                    shape = MaterialTheme.shapes.large
                )
                OutlinedTextField(
                    value = reps,
                    onValueChange = { reps = it },
                    label = { Text("Reps") },
                    modifier = Modifier.weight(1f),
                    leadingIcon = { Icon(Icons.Default.Repeat, null) },
                    shape = MaterialTheme.shapes.large
                )
            }

            OutlinedTextField(
                value = duration,
                onValueChange = { duration = it },
                label = { Text("Duration") },
                placeholder = { Text("e.g. 15 mins") },
                modifier = Modifier.fillMaxWidth(),
                leadingIcon = { Icon(Icons.Default.Timer, null) },
                shape = MaterialTheme.shapes.large
            )

            Spacer(modifier = Modifier.weight(1f))

            GradientButton(
                text = "Save Workout",
                onClick = onWorkoutSaved
            )
        }
    }
}
