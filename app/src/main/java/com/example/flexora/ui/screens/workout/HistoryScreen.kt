package com.example.flexora.ui.screens.workout

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.flexora.ui.components.PremiumCard
import com.example.flexora.ui.theme.PrimaryPurple

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoryScreen() {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Workout History", fontWeight = FontWeight.Bold) },
                actions = {
                    IconButton(onClick = { }) {
                        Icon(Icons.Default.Search, contentDescription = "Search")
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color.Transparent
                )
            )
        }
    ) { padding ->
        val historyItems = listOf(
            HistoryItem("Leg Day", "12 Nov 2024", "1h 10m", "6 Exercises"),
            HistoryItem("Full Body", "10 Nov 2024", "55 min", "8 Exercises"),
            HistoryItem("Push Day", "08 Nov 2024", "1h 05m", "5 Exercises"),
            HistoryItem("Pull Day", "06 Nov 2024", "1h 15m", "7 Exercises"),
            HistoryItem("Cardio & Core", "04 Nov 2024", "45 min", "4 Exercises")
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentPadding = PaddingValues(20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(historyItems) { item ->
                HistoryCard(item)
            }
        }
    }
}

data class HistoryItem(val name: String, val date: String, val duration: String, val exercises: String)

@Composable
fun HistoryCard(item: HistoryItem) {
    PremiumCard {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                shape = MaterialTheme.shapes.medium,
                color = PrimaryPurple.copy(alpha = 0.1f),
                modifier = Modifier.size(48.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        imageVector = Icons.Default.FitnessCenter,
                        contentDescription = null,
                        tint = PrimaryPurple,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
            
            Spacer(modifier = Modifier.width(16.dp))
            
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    item.name,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    "${item.exercises} • ${item.duration}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                )
            }
            
            Text(
                item.date,
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f)
            )
        }
    }
}
