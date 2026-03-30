package com.example.flexora.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(val route: String, val title: String = "", val icon: ImageVector? = null) {
    object Login : Screen("login")
    object Register : Screen("register")
    object Dashboard : Screen("dashboard", "Home", Icons.Default.Home)
    object WorkoutHistory : Screen("history", "History", Icons.Default.History)
    object Profile : Screen("profile", "Profile", Icons.Default.Person)
    object AddWorkout : Screen("add_workout", "Add", Icons.Default.Add)
}

val bottomNavItems = listOf(
    Screen.Dashboard,
    Screen.WorkoutHistory,
    Screen.Profile
)
