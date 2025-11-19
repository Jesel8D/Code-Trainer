package com.CodeTrainer.codetrainer.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.CodeTrainer.codetrainer.ui.features.exercisedetail.ExerciseDetailRoute
import com.CodeTrainer.codetrainer.ui.features.help.HelpDetailRoute
import com.CodeTrainer.codetrainer.ui.features.help.HelpRoute
import com.CodeTrainer.codetrainer.ui.features.home.HomeRoute
import com.CodeTrainer.codetrainer.ui.features.login.LoginRoute
import com.CodeTrainer.codetrainer.ui.features.register.RegisterRoute
import com.CodeTrainer.codetrainer.ui.features.splash.SplashRoute

object Routes {
    const val SPLASH = "splash"
    const val LOGIN = "login"
    const val REGISTER = "register"
    const val HOME = "home"
    const val EXERCISE_DETAIL = "exercise_detail/{exerciseId}"
    const val HELP = "help"
    const val HELP_DETAIL = "help_detail/{topicId}"

    fun exerciseDetail(exerciseId: Int) = "exercise_detail/$exerciseId"
    fun helpDetail(topicId: Int) = "help_detail/$topicId"
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Routes.SPLASH
    ) {

        composable(Routes.SPLASH) {
            SplashRoute(
                onNavigateToLogin = {
                    navController.navigate(Routes.LOGIN) {
                        popUpTo(Routes.SPLASH) { inclusive = true }
                    }
                },
                onNavigateToHome = {
                    navController.navigate(Routes.HOME) {
                        popUpTo(Routes.SPLASH) { inclusive = true }
                    }
                }
            )
        }

        composable(Routes.LOGIN) {
            LoginRoute(
                onNavigateToRegister = {
                    navController.navigate(Routes.REGISTER)
                },
                onLoginSuccess = {
                    navController.navigate(Routes.HOME) {
                        popUpTo(Routes.LOGIN) { inclusive = true }
                    }
                }
            )
        }

        composable(Routes.REGISTER) {
            RegisterRoute(
                onNavigateBack = { navController.popBackStack() },
                onRegisterSuccess = {
                    navController.navigate(Routes.HOME) {
                        popUpTo(Routes.LOGIN) { inclusive = true }
                    }
                }
            )
        }

        composable(Routes.HOME) {
            HomeRoute(
                onNavigateToExerciseDetail = { exerciseId ->
                    navController.navigate(Routes.exerciseDetail(exerciseId))
                },
                onNavigateToHelp = {
                    navController.navigate(Routes.HELP)
                },
                onLogout = {
                    navController.navigate(Routes.LOGIN) {
                        popUpTo(Routes.HOME) { inclusive = true }
                    }
                }
            )
        }

        composable(
            route = Routes.EXERCISE_DETAIL,
            arguments = listOf(
                navArgument("exerciseId") { type = NavType.IntType }
            )
        ) {
            ExerciseDetailRoute(
                onNavigateBack = { navController.popBackStack() }
            )
        }

        // NUEVO: Ruta de centro de ayuda
        composable(Routes.HELP) {
            HelpRoute(
                onNavigateBack = { navController.popBackStack() },
                onTopicClick = { topicId ->
                    navController.navigate(Routes.helpDetail(topicId))
                }
            )
        }

        // NUEVO: Ruta de detalle de topic de ayuda
        composable(
            route = Routes.HELP_DETAIL,
            arguments = listOf(
                navArgument("topicId") { type = NavType.IntType }
            )
        ) {
            HelpDetailRoute(
                onNavigateBack = { navController.popBackStack() }
            )
        }
    }
}