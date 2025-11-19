package com.CodeTrainer.codetrainer.ui.features.home

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.CodeTrainer.codetrainer.ui.features.dashboard.DashboardRoute
import com.CodeTrainer.codetrainer.ui.features.exerciselist.ExerciseListRoute
import com.CodeTrainer.codetrainer.ui.features.profile.ProfileRoute
import com.CodeTrainer.codetrainer.ui.features.settings.SettingsRoute
import com.CodeTrainer.codetrainer.ui.navigation.BottomBarRoutes

data class BottomNavItem(
    val route: String,
    val title: String,
    val icon: ImageVector
)

@Composable
fun HomeScreen(
    onNavigateToExerciseDetail: (Int) -> Unit,
    onNavigateToHelp: () -> Unit,
    onLogout: () -> Unit
) {
    val navController = rememberNavController()

    val bottomNavItems = listOf(
        BottomNavItem(
            route = BottomBarRoutes.Dashboard.route,
            title = BottomBarRoutes.Dashboard.title,
            icon = Icons.Default.Dashboard
        ),
        BottomNavItem(
            route = BottomBarRoutes.Exercises.route,
            title = BottomBarRoutes.Exercises.title,
            icon = Icons.Default.Code
        ),
        BottomNavItem(
            route = BottomBarRoutes.Profile.route,
            title = BottomBarRoutes.Profile.title,
            icon = Icons.Default.Person
        ),
        BottomNavItem(
            route = BottomBarRoutes.Settings.route,
            title = BottomBarRoutes.Settings.title,
            icon = Icons.Default.Settings
        )
    )

    Scaffold(
        bottomBar = {
            BottomNavigationBar(
                navController = navController,
                items = bottomNavItems
            )
        }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = BottomBarRoutes.Dashboard.route,
            modifier = Modifier.padding(paddingValues)
        ) {
            composable(BottomBarRoutes.Dashboard.route) {
                DashboardRoute(
                    onNavigateToExercises = {
                        navController.navigate(BottomBarRoutes.Exercises.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    onNavigateToHelp = onNavigateToHelp
                )
            }

            composable(BottomBarRoutes.Exercises.route) {
                ExerciseListRoute(
                    onNavigateToDetail = onNavigateToExerciseDetail
                )
            }

            composable(BottomBarRoutes.Profile.route) {
                ProfileRoute(
                    onLogout = onLogout
                )
            }

            composable(BottomBarRoutes.Settings.route) {
                SettingsRoute(
                    onNavigateToHelp = onNavigateToHelp
                )
            }
        }
    }
}

@Composable
private fun BottomNavigationBar(
    navController: NavHostController,
    items: List<BottomNavItem>
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    NavigationBar {
        items.forEach { item ->
            val selected = currentDestination?.hierarchy?.any { it.route == item.route } == true

            NavigationBarItem(
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.title
                    )
                },
                label = { Text(item.title) },
                selected = selected,
                onClick = {
                    navController.navigate(item.route) {
                        // Pop hasta el inicio del grafo para evitar acumulación
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        // Evitar múltiples copias del mismo destino
                        launchSingleTop = true
                        // Restaurar estado al regresar
                        restoreState = true
                    }
                }
            )
        }
    }
}
