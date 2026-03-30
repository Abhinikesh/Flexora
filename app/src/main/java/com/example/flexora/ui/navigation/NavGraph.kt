package com.example.flexora.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.flexora.ui.screens.analytics.AnalyticsScreen
import com.example.flexora.ui.screens.auth.LoginScreen
import com.example.flexora.ui.screens.auth.RegisterScreen
import com.example.flexora.ui.screens.dashboard.DashboardScreen
import com.example.flexora.ui.screens.profile.ProfileScreen
import com.example.flexora.ui.screens.workout.AddWorkoutScreen
import com.example.flexora.ui.screens.workout.HistoryScreen
import com.example.flexora.ui.theme.PrimaryPurple
import com.example.flexora.ui.viewmodel.ThemeViewModel
import com.example.flexora.ui.viewmodel.WorkoutViewModel

@Composable
fun FlexoraNavGraph() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val workoutViewModel: WorkoutViewModel = hiltViewModel()
    val themeViewModel: ThemeViewModel = hiltViewModel()

    val showBottomBar = bottomNavItems.any { it.route == currentDestination?.route }

    Scaffold(
        bottomBar = {
            if (showBottomBar) {
                NavigationBar(
                    containerColor = MaterialTheme.colorScheme.surface,
                    tonalElevation = 8.dp
                ) {
                    bottomNavItems.forEach { screen ->
                        val selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true
                        NavigationBarItem(
                            icon = { 
                                screen.icon?.let { 
                                    Icon(it, contentDescription = screen.title) 
                                } 
                            },
                            label = { Text(screen.title, fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal) },
                            selected = selected,
                            onClick = {
                                navController.navigate(screen.route) {
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            },
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = PrimaryPurple,
                                selectedTextColor = PrimaryPurple,
                                indicatorColor = PrimaryPurple.copy(alpha = 0.1f)
                            )
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Login.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.Login.route) {
                LoginScreen(
                    onNavigateToRegister = { navController.navigate(Screen.Register.route) },
                    onLoginSuccess = {
                        navController.navigate(Screen.Dashboard.route) {
                            popUpTo(Screen.Login.route) { inclusive = true }
                        }
                    }
                )
            }
            composable(Screen.Register.route) {
                RegisterScreen(
                    onNavigateToLogin = { navController.navigate(Screen.Login.route) },
                    onRegisterSuccess = {
                        navController.navigate(Screen.Dashboard.route) {
                            popUpTo(Screen.Register.route) { inclusive = true }
                        }
                    }
                )
            }
            composable(Screen.Dashboard.route) {
                val todayWorkouts by workoutViewModel.todayWorkouts.collectAsState()
                val suggestion by workoutViewModel.suggestion
                
                DashboardScreen(
                    onNavigateToAddWorkout = { navController.navigate(Screen.AddWorkout.route) },
                    todayWorkouts = todayWorkouts,
                    suggestion = suggestion,
                    onUpdateProgress = { workout, reps, sets ->
                        workoutViewModel.updateProgress(workout, reps, sets)
                    }
                )
            }
            composable(Screen.Analytics.route) {
                val allWorkouts by workoutViewModel.allWorkouts.collectAsState()
                AnalyticsScreen(workouts = allWorkouts)
            }
            composable(Screen.WorkoutHistory.route) {
                HistoryScreen()
            }
            composable(Screen.Profile.route) {
                val isDark by themeViewModel.isDarkMode.collectAsState()
                ProfileScreen(
                    isDarkMode = isDark ?: false,
                    onToggleDarkMode = { themeViewModel.toggleDarkMode(it) }
                )
            }
            composable(Screen.AddWorkout.route) {
                AddWorkoutScreen(
                    onWorkoutSaved = { navController.popBackStack() },
                    onNavigateBack = { navController.popBackStack() }
                )
            }
        }
    }
}
