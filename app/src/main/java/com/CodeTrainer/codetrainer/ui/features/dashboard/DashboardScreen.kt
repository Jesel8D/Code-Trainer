package com.CodeTrainer.codetrainer.ui.features.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.ListAlt
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
// import androidx.lifecycle.compose.collectAsStateWithLifecycle // <-- CAMBIO: Esta línea se elimina
import androidx.compose.runtime.collectAsState // <-- CAMBIO: Añadimos esta
import com.CodeTrainer.codetrainer.domain.model.ExerciseDetails
import com.CodeTrainer.codetrainer.domain.model.Stats

// 1. La "Ruta" (Route) que Hilt y Navegación llaman
@Composable
fun DashboardRoute(
    viewModel: DashboardViewModel = hiltViewModel(),
    onNavigateToExercises: () -> Unit
) {
    // 3. ¡AQUÍ ESTÁ EL CAMBIO!
    // val uiState by viewModel.uiState.collectAsStateWithLifecycle() // <-- ESTA LÍNEA CRASHEA
    val uiState by viewModel.uiState.collectAsState() // <-- USAMOS ESTA EN SU LUGAR

    DashboardScreen(
        uiState = uiState,
        onNavigateToExercises = onNavigateToExercises
    )
}

// 2. La Pantalla (Screen) "tonta" que solo pinta el estado
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    uiState: DashboardUiState,
    onNavigateToExercises: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Dashboard") },
                navigationIcon = {
                    IconButton(onClick = { /* TODO: Abrir Drawer */ }) {
                        Icon(Icons.Default.Menu, contentDescription = "Menú")
                    }
                },
                actions = {
                    IconButton(onClick = { /* TODO: Abrir Notificaciones */ }) {
                        Icon(Icons.Default.Notifications, contentDescription = "Notificaciones")
                    }
                }
            )
        },
        // TODO: Añadir el BottomNavigationBar
    ) { paddingValues ->

        if (uiState.isLoading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(horizontal = 16.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                Spacer(modifier = Modifier.height(16.dp))

                // Saludo "Hola, Alex"
                val userName = uiState.user?.displayName?.split(" ")?.first() ?: "Usuario"
                Text(
                    text = "Hola, $userName",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Card de Progreso Semanal
                StatsCard(
                    icon = Icons.Default.BarChart,
                    title = "Progreso Semanal",
                    stats = uiState.stats,
                    onClick = onNavigateToExercises
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Card de Ejercicios Pendientes
                PendingCard(
                    icon = Icons.Default.ListAlt,
                    title = "Ejercicios Pendientes",
                    pendingExercises = uiState.pendingExercises,
                    onClick = onNavigateToExercises
                )
            }
        }
    }
}

// 3. El Composable para la Card de "Progreso Semanal"
@Composable
fun StatsCard(
    icon: ImageVector,
    title: String,
    stats: Stats?,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(40.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text(text = title, style = MaterialTheme.typography.titleLarge)
                    val exercisesText = stats?.totalExercisesCompleted ?: 0
                    val streakText = stats?.dailyStreak ?: 0
                    Text(
                        text = "Ejercicios completados: $exercisesText/10, racha: $streakText días",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = onClick,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("Ver ejercicios")
            }
        }
    }
}

// 4. El Composable para la Card de "Ejercicios Pendientes"
@Composable
fun PendingCard(
    icon: ImageVector,
    title: String,
    pendingExercises: List<ExerciseDetails>,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(40.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text(text = title, style = MaterialTheme.typography.titleLarge)
                    Text(
                        text = "${pendingExercises.size} ejercicios por completar",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = onClick,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("Ver ejercicios")
            }
        }
    }
}