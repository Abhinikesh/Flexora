package com.example.flexora.ui.screens.workout

import android.view.LayoutInflater
import android.view.View
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.viewinterop.AndroidView
import androidx.recyclerview.widget.RecyclerView
import com.example.flexora.R
import com.example.flexora.domain.model.Workout
import com.example.flexora.ui.adapter.WorkoutAdapter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoryScreen(workouts: List<Workout>) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Workout History", fontWeight = FontWeight.Bold) },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color.Transparent
                )
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            if (workouts.isEmpty()) {
                Text(
                    text = "No workouts yet",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                    modifier = Modifier.align(Alignment.Center)
                )
            } else {
                AndroidView(
                    factory = { context ->
                        val view = LayoutInflater.from(context).inflate(R.layout.screen_history, null)
                        val recyclerView = view.findViewById<RecyclerView>(R.id.rvHistory)
                        val adapter = WorkoutAdapter()
                        recyclerView.adapter = adapter
                        adapter.submitList(workouts)
                        view
                    },
                    update = { view ->
                        val recyclerView = view.findViewById<RecyclerView>(R.id.rvHistory)
                        val adapter = recyclerView.adapter as? WorkoutAdapter
                        adapter?.submitList(workouts)
                    },
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}
