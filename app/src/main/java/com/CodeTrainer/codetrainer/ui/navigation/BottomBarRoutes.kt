package com.CodeTrainer.codetrainer.ui.navigation

sealed class BottomBarRoutes(val route: String, val title: String) {
    object Dashboard : BottomBarRoutes("dashboard", "Dashboard")
    object Exercises : BottomBarRoutes("exercises", "Ejercicios")
    object Profile : BottomBarRoutes("profile", "Perfil")
    object Settings : BottomBarRoutes("settings", "Ajustes")
}
