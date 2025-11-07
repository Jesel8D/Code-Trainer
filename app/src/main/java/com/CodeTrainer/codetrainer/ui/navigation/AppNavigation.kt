package com.CodeTrainer.codetrainer.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.CodeTrainer.codetrainer.ui.features.exerciselist.ExerciseListRoute

object Routes {
    const val EXERCISE_LIST = "exercise_list"
    const val EXERCISE_DETAIL = "exercise_detail" // La usaremos después
    const val DASHBOARD = "dashboard" // La usaremos después
}


// (El objeto Routes que creamos antes va aquí arriba)

// 2. Este es el "mapa" principal de tu aplicación
@Composable
fun AppNavigation() {
    // 3. Crea el controlador que gestiona el estado de la navegación
    val navController = rememberNavController()

    // 4. Define el "host" (el contenedor) de todas tus pantallas
    NavHost(
        navController = navController,
        startDestination = Routes.EXERCISE_LIST // 5. La primera pantalla que se mostrará
    ) {

        // 6. Define la primera pantalla
        composable(Routes.EXERCISE_LIST) {
            // Llama al Composable "Route" que creamos,
            // Hilt se encargará de proveer el ViewModel.
            ExerciseListRoute()
        }

        /*
        // 7. Así es como añadiremos más pantallas en el futuro:
        composable(Routes.DASHBOARD) {
            // DashboardRoute()
        }

        composable(Routes.EXERCISE_DETAIL) {
            // ExerciseDetailRoute()
        }
        */
    }
}