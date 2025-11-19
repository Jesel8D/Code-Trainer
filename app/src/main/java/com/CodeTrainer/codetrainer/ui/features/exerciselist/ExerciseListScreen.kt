package com.CodeTrainer.codetrainer.ui.features.exerciselist

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.CodeTrainer.codetrainer.domain.model.ExerciseDetails
import com.CodeTrainer.codetrainer.domain.model.ProgressStatus

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExerciseListScreen(
    uiState: ExerciseListUiState,
    onExerciseClick: (Int) -> Unit = {},
    onLanguageFilterChange: (String) -> Unit = {},
    onLevelFilterChange: (String) -> Unit = {},
    onClearFilters: () -> Unit = {}
) {
    var showFilters by remember { mutableStateOf(true) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Ejercicios") },
                actions = {
                    IconButton(onClick = { showFilters = !showFilters }) {
                        Icon(
                            imageVector = Icons.Default.FilterList,
                            contentDescription = if (showFilters) "Ocultar filtros" else "Mostrar filtros"
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        when {
            uiState.isLoading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
            uiState.error != null -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Error: ${uiState.error}",
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }
            else -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                ) {
                    // Sección de filtros
                    if (showFilters) {
                        FiltersSection(
                            selectedLanguage = uiState.selectedLanguage,
                            selectedLevel = uiState.selectedLevel,
                            onLanguageChange = onLanguageFilterChange,
                            onLevelChange = onLevelFilterChange,
                            onClearFilters = onClearFilters
                        )
                        Divider()
                    }

                    // Contador de resultados
                    if (uiState.filteredExercises.isNotEmpty()) {
                        Text(
                            text = "${uiState.filteredExercises.size} ejercicio(s) encontrado(s)",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                        )
                    }

                    // Lista de ejercicios
                    if (uiState.filteredExercises.isEmpty()) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Text(
                                    text = "No hay ejercicios que coincidan con los filtros",
                                    style = MaterialTheme.typography.bodyLarge
                                )
                                if (uiState.selectedLanguage != "Todos" || uiState.selectedLevel != "Todos") {
                                    TextButton(onClick = onClearFilters) {
                                        Text("Limpiar filtros")
                                    }
                                }
                            }
                        }
                    } else {
                        LazyColumn(
                            contentPadding = PaddingValues(16.dp),
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            items(uiState.filteredExercises) { exerciseDetail ->
                                ExerciseCard(
                                    exerciseDetail = exerciseDetail,
                                    onClick = { onExerciseClick(exerciseDetail.exercise.id) }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun FiltersSection(
    selectedLanguage: String,
    selectedLevel: String,
    onLanguageChange: (String) -> Unit,
    onLevelChange: (String) -> Unit,
    onClearFilters: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Header de filtros
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Filtros",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )

            if (selectedLanguage != "Todos" || selectedLevel != "Todos") {
                TextButton(onClick = onClearFilters) {
                    Text("Limpiar")
                }
            }
        }

        // Filtro de lenguaje
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text(
                text = "Lenguaje",
                style = MaterialTheme.typography.labelLarge,
                fontWeight = FontWeight.SemiBold
            )
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                val languages = listOf("Todos", "Python", "C++")
                items(languages.size) { index ->
                    val language = languages[index]
                    FilterChip(
                        selected = selectedLanguage == language,
                        onClick = { onLanguageChange(language) },
                        label = { Text(language) }
                    )
                }
            }
        }

        // Filtro de nivel
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text(
                text = "Dificultad",
                style = MaterialTheme.typography.labelLarge,
                fontWeight = FontWeight.SemiBold
            )
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                val levels = listOf("Todos", "Básico", "Intermedio", "Avanzado")
                items(levels.size) { index ->
                    val level = levels[index]
                    FilterChip(
                        selected = selectedLevel == level,
                        onClick = { onLevelChange(level) },
                        label = { Text(level) }
                    )
                }
            }
        }
    }
}

@Composable
private fun ExerciseCard(
    exerciseDetail: ExerciseDetails,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = exerciseDetail.exercise.title,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = exerciseDetail.exercise.description,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        maxLines = 2
                    )
                }

                if (exerciseDetail.progress?.status == ProgressStatus.COMPLETED) {
                    Icon(
                        imageVector = Icons.Default.CheckCircle,
                        contentDescription = "Completado",
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                AssistChip(
                    onClick = { },
                    label = { Text(exerciseDetail.exercise.language) },
                    leadingIcon = {
                        Text(
                            text = when (exerciseDetail.exercise.language) {
                                "Python" -> "🐍"
                                "C++" -> "⚙️"
                                else -> "💻"
                            },
                            style = MaterialTheme.typography.labelMedium
                        )
                    }
                )
                AssistChip(
                    onClick = { },
                    label = { Text(exerciseDetail.exercise.level) },
                    colors = AssistChipDefaults.assistChipColors(
                        containerColor = when (exerciseDetail.exercise.level) {
                            "Básico" -> MaterialTheme.colorScheme.tertiaryContainer
                            "Intermedio" -> MaterialTheme.colorScheme.secondaryContainer
                            "Avanzado" -> MaterialTheme.colorScheme.errorContainer
                            else -> MaterialTheme.colorScheme.surfaceVariant
                        }
                    )
                )
            }
        }
    }
}