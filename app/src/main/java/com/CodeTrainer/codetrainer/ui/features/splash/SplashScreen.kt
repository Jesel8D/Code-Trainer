package com.CodeTrainer.codetrainer.ui.features.splash

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
// import androidx.lifecycle.compose.collectAsStateWithLifecycle // <-- CAMBIO: Esta línea se elimina
import androidx.compose.runtime.collectAsState // <-- CAMBIO: Añadimos esta

@Composable
fun SplashRoute(
    viewModel: SplashViewModel = hiltViewModel(),
    onNavigateToLogin: () -> Unit, // 1. Recibe la "instrucción" de navegación
    onNavigateToHome: () -> Unit   // 2. Recibe la otra "instrucción"
) {
    // 3. Observa el estado del ViewModel
    // val authStatus by viewModel.authStatus.collectAsStateWithLifecycle() // <-- CAMBIO: Esta línea crashea
    val authStatus by viewModel.authStatus.collectAsState() // <-- CAMBIO: Usamos esta en su lugar

    // 4. LaunchedEffect es CLAVE. Se ejecuta 1 vez cuando el estado cambia.
    LaunchedEffect(authStatus) {
        when (authStatus) {
            is AuthStatus.Authenticated -> {
                // 5. Llama a la instrucción de navegación
                onNavigateToHome()
            }

            is AuthStatus.Unauthenticated -> {
                // 6. Llama a la otra instrucción
                onNavigateToLogin()
            }

            is AuthStatus.Loading -> {
                // No hace nada, solo espera
            }
        }
    }

    // 7. Muestra la UI "tonta"
    SplashScreen()
}

@Composable
fun SplashScreen(modifier: Modifier = Modifier) {
    // Esta es tu pantalla de logo
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        // TODO: Reemplazar esto con tu logo
        CircularProgressIndicator()
        // Text(text = "CodeTrainer")
    }
}