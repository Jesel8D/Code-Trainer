package com.CodeTrainer.codetrainer.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.CodeTrainer.codetrainer.ui.features.exerciselist.ExerciseListRoute
import com.CodeTrainer.codetrainer.ui.features.login.LoginRoute
import com.CodeTrainer.codetrainer.ui.features.register.RegisterRoute
import com.CodeTrainer.codetrainer.ui.features.splash.SplashRoute
import com.CodeTrainer.codetrainer.ui.features.dashboard.DashboardRoute

object Routes {
    const val SPLASH = "splash"
    const val LOGIN = "login"
    const val REGISTER = "register"

    const val DASHBOARD = "dashboard"
    const val EXERCISE_LIST = "exercise_list"

}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Routes.SPLASH
    ) {

        // Pantalla Splash
        composable(Routes.SPLASH) {
            SplashRoute(
                onNavigateToLogin = {
                    navController.navigate(Routes.LOGIN) {
                        popUpTo(Routes.SPLASH) { inclusive = true }
                    }
                },
                onNavigateToHome = {
                    // Cambiamos el destino a vista 'DASHBOARD'
                    navController.navigate(Routes.DASHBOARD) {
                        popUpTo(Routes.SPLASH) {
                            inclusive = true
                        }
                    }
                }
            )
        }

        // --- ¡AQUÍ CONECTAMOS LA PANTALLA DE LOGIN! ---
        composable(Routes.LOGIN) {
            LoginRoute(
                onNavigateToRegister = {
                    // Navega a la pantalla de registro
                    navController.navigate(Routes.REGISTER)
                },
                onLoginSuccess = {
                    //Cambiamos a vista 'DASHBOARD'
                    navController.navigate(Routes.DASHBOARD) {
                        popUpTo(Routes.LOGIN) {
                            inclusive = true
                        }
                    }
                }
            )
        }

        // --- ¡AQUÍ CONECTAMOS LA PANTALLA DE REGISTRO! ---
        composable(Routes.REGISTER) {
            RegisterRoute(
                onNavigateBack = {
                    navController.popBackStack() // Regresa a la pantalla anterior (Login)
                },
                //Existo, navega a la home y limpia todo el historial de auth
                onRegisterSuccess = {
                    // Cambiamos el destino a 'DASHBOARD'
                    navController.navigate(Routes.DASHBOARD) {
                        popUpTo(Routes.LOGIN) {
                            inclusive = true
                        }
                    }
                }
            )
        }

        // Pantalla de Lista de Ejercicios
        composable(Routes.EXERCISE_LIST) {
            ExerciseListRoute()
        }

        //Nuevo composable para DASHBOARD
        composable(Routes.DASHBOARD) {
            DashboardRoute(
                onNavigateToExercises = {
                    navController.navigate(Routes.EXERCISE_LIST)
                }
            )
        }

        // Esta pantalla se mantiene, ahora se accede desde el Dashboard
        composable(Routes.EXERCISE_LIST) {
            ExerciseListRoute()
        }


    }
}