package com.CodeTrainer.codetrainer.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.CodeTrainer.codetrainer.ui.features.exerciselist.ExerciseListRoute
import com.CodeTrainer.codetrainer.ui.features.login.LoginRoute
import com.CodeTrainer.codetrainer.ui.features.register.RegisterRoute
import com.CodeTrainer.codetrainer.ui.features.splash.SplashRoute
import okhttp3.Route

object Routes {
    const val SPLASH = "splash"
    const val LOGIN = "login"
    const val REGISTER = "register"
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
                    navController.navigate(Routes.EXERCISE_LIST) {
                        popUpTo(Routes.SPLASH) { inclusive = true }
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
                    // Navega a la lista y borra TODO el historial de auth
                    navController.navigate(Routes.EXERCISE_LIST) {
                        popUpTo(Routes.LOGIN) { inclusive = true }
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
                    //En caso de exito, navega a la gome y limpia todo el historial de auth
                    navController.navigate(Routes.EXERCISE_LIST) {
                        popUpTo(Routes.LOGIN) { inclusive = true } //Borra login y register
                    }
                }
            )
        }

        // Pantalla de Lista de Ejercicios
        composable(Routes.EXERCISE_LIST) {
            ExerciseListRoute()
        }
    }
}