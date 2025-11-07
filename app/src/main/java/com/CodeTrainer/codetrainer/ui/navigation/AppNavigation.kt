package com.CodeTrainer.codetrainer.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.CodeTrainer.codetrainer.ui.features.exerciselist.ExerciseListRoute
import com.CodeTrainer.codetrainer.ui.features.splash.SplashRoute // <-- 1. IMPORTAR

// 1. Añadimos las nuevas rutas
object Routes {
    const val SPLASH = "splash" // <-- Nueva
    const val LOGIN = "login"   // <-- Nueva
    const val REGISTER = "register" // <-- Nueva

    const val EXERCISE_LIST = "exercise_list"
    // (quitamos "DASHBOARD" y "EXERCISE_DETAIL" por ahora para simplificar)
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Routes.SPLASH // 2. ¡La nueva pantalla de inicio!
    ) {

        // 3. NUEVA PANTALLA: Splash
        composable(Routes.SPLASH) {
            SplashRoute(
                // Le pasamos lambdas para decirle qué hacer al terminar
                onNavigateToLogin = {
                    // Navega a Login y borra Splash del historial
                    navController.navigate(Routes.LOGIN) {
                        popUpTo(Routes.SPLASH) { inclusive = true }
                    }
                },
                onNavigateToHome = {
                    // Navega a Home y borra Splash del historial
                    navController.navigate(Routes.EXERCISE_LIST) {
                        popUpTo(Routes.SPLASH) { inclusive = true }
                    }
                }
            )
        }

        // 4. NUEVA PANTALLA: Login (Placeholder)
        composable(Routes.LOGIN) {
            // TODO: Crear LoginScreen.kt
            // LoginRoute(onNavigateToRegister = { ... }, onLoginSuccess = { ... })
        }

        // 5. NUEVA PANTALLA: Register (Placeholder)
        composable(Routes.REGISTER) {
            // TODO: Crear RegisterScreen.kt
            // RegisterRoute(onRegisterSuccess = { ... })
        }

        // 6. Pantalla de Lista de Ejercicios (ya la teníamos)
        composable(Routes.EXERCISE_LIST) {
            ExerciseListRoute()
        }
    }
}