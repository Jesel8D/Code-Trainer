package com.CodeTrainer.codetrainer.ui.features.exerciselist

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
// import androidx.lifecycle.compose.collectAsStateWithLifecycle // <-- CAMBIO: Esta línea se elimina
import androidx.compose.runtime.collectAsState // <-- CAMBIO: Añadimos esta
import com.CodeTrainer.codetrainer.domain.model.ExerciseDetails

// 1. Este es el Composable "raíz" que llamará la Navegación
@Composable
fun ExerciseListRoute(
    // 2. Hilt nos provee el ViewModel automáticamente
    viewModel: ExerciseListViewModel = hiltViewModel()
) {
    // 3. Recolectamos el estado del ViewModel de forma segura
    // val uiState by viewModel.uiState.collectAsStateWithLifecycle() // <-- CAMBIO: Esta línea crashea
    val uiState by viewModel.uiState.collectAsState() // <-- CAMBIO: Usamos esta en su lugar

    // 4. Pasamos el estado al Composable "tonto"
    ExerciseListScreen(uiState = uiState)
}

// 5. Este es el Composable "tonto" (dumb). No sabe nada de ViewModels.
//    Solo sabe cómo pintar un estado.
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExerciseListScreen(
    uiState: ExerciseListUiState
) {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Ejercicios") })
        }
    ) { paddingValues ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentAlignment = Alignment.Center
        ) {
            // 6. Decidimos qué pintar basándonos en el estado
            when (uiState) {
                is ExerciseListUiState.Loading -> {
                    CircularProgressIndicator()
                }

                is ExerciseListUiState.Success -> {
                    // ¡Mostramos la lista!
                    ExerciseList(exercises = uiState.exercises)
                }

                is ExerciseListUiState.Error -> {
                    Text(text = "Error: ${uiState.message}")
                }
            }
        }
    }
}

// 7. Un Composable simple para mostrar la lista
@Composable
fun ExerciseList(exercises: List<ExerciseDetails>, modifier: Modifier = Modifier) {
    if (exercises.isEmpty()) {
        Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(text = "No hay ejercicios.")
        }
    } else {
        LazyColumn(modifier = modifier.fillMaxSize()) {
            items(exercises) { details ->
                // TODO: Crear un Composable "ExerciseCard" bonito
                Text(
                    text = "${details.exercise.title} - ${details.progress?.status ?: "PENDING"}",
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
    }
}