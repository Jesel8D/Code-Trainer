package com.CodeTrainer.codetrainer.ui.features.splash

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Code
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.runtime.collectAsState

@Composable
fun SplashRoute(
    viewModel: SplashViewModel = hiltViewModel(),
    onNavigateToLogin: () -> Unit, // 1. Recibe la "instrucción" de navegación
    onNavigateToHome: () -> Unit   // 2. Recibe la otra "instrucción"
) {
    // 3. Observa el estado del ViewModel (ya corregido)
    val authStatus by viewModel.authStatus.collectAsState()

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
    // Esta es tu nueva pantalla de logo (basada en tu diseño)
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // TODO: Reemplazar esto con tu logo
            Icon(
                imageVector = Icons.Default.Code,
                contentDescription = "CodeTrainer Logo",
                modifier = Modifier.size(120.dp),
                tint = MaterialTheme.colorScheme.primary
            )
            Text(
                text = "CodeTrainer",
                style = MaterialTheme.typography.headlineMedium
            )
            CircularProgressIndicator()
        }
    }
}