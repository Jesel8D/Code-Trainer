package com.CodeTrainer.codetrainer.ui.features.exercisedetail

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.CodeTrainer.codetrainer.domain.model.ExerciseDetails
import com.CodeTrainer.codetrainer.domain.model.ProgressStatus

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExerciseDetailScreen(
    uiState: ExerciseDetailUiState,
    onNavigateBack: () -> Unit,
    onSolutionChange: (String) -> Unit,
    onToggleHint: () -> Unit,
    onToggleSolution: () -> Unit,
    onSubmit: () -> Unit,
    onClearError: () -> Unit,
    onClearSuccess: () -> Unit
) {
    // Snackbar host para mensajes
    val snackbarHostState = remember { SnackbarHostState() }

    // Mostrar mensaje de error
    LaunchedEffect(uiState.error) {
        uiState.error?.let { error ->
            snackbarHostState.showSnackbar(
                message = error,
                duration = SnackbarDuration.Short
            )
            onClearError()
        }
    }

    // Mostrar mensaje de éxito
    LaunchedEffect(uiState.saveSuccess) {
        if (uiState.saveSuccess) {
            snackbarHostState.showSnackbar(
                message = "¡Solución guardada con éxito!",
                duration = SnackbarDuration.Short
            )
            onClearSuccess()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = uiState.exerciseDetails?.exercise?.title ?: "Ejercicio",
                        maxLines = 1
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Volver"
                        )
                    }
                }
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
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
            uiState.exerciseDetails == null -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Text(
                            text = "Ejercicio no encontrado",
                            style = MaterialTheme.typography.titleLarge
                        )
                        Button(onClick = onNavigateBack) {
                            Text("Volver")
                        }
                    }
                }
            }
            else -> {
                ExerciseDetailContent(
                    modifier = Modifier.padding(paddingValues),
                    exerciseDetails = uiState.exerciseDetails,
                    userSolution = uiState.userSolution,
                    showHint = uiState.showHint,
                    showSolution = uiState.showSolution,
                    isSaving = uiState.isSaving,
                    onSolutionChange = onSolutionChange,
                    onToggleHint = onToggleHint,
                    onToggleSolution = onToggleSolution,
                    onSubmit = onSubmit
                )
            }
        }
    }
}

@Composable
private fun ExerciseDetailContent(
    modifier: Modifier = Modifier,
    exerciseDetails: ExerciseDetails,
    userSolution: String,
    showHint: Boolean,
    showSolution: Boolean,
    isSaving: Boolean,
    onSolutionChange: (String) -> Unit,
    onToggleHint: () -> Unit,
    onToggleSolution: () -> Unit,
    onSubmit: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Header con metadata
        ExerciseHeader(exerciseDetails = exerciseDetails)

        // Descripción del ejercicio
        ExerciseDescriptionCard(description = exerciseDetails.exercise.description)

        // Hint (expandible)
        HintCard(
            hint = exerciseDetails.exercise.hint,
            showHint = showHint,
            onToggleHint = onToggleHint
        )

        // Editor de código
        CodeEditorCard(
            userSolution = userSolution,
            language = exerciseDetails.exercise.language,
            onSolutionChange = onSolutionChange
        )

        // Solución sugerida (expandible)
        SolutionCard(
            solution = exerciseDetails.exercise.solutionCode,
            showSolution = showSolution,
            onToggleSolution = onToggleSolution
        )

        // Botón de enviar
        Button(
            onClick = onSubmit,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            enabled = !isSaving && userSolution.isNotBlank()
        ) {
            if (isSaving) {
                CircularProgressIndicator(
                    modifier = Modifier.size(24.dp),
                    color = MaterialTheme.colorScheme.onPrimary
                )
            } else {
                Icon(
                    imageVector = Icons.Default.Send,
                    contentDescription = null,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("Enviar solución")
            }
        }

        // Espaciado final
        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
private fun ExerciseHeader(exerciseDetails: ExerciseDetails) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AssistChip(
            onClick = { },
            label = { Text(exerciseDetails.exercise.language) },
            leadingIcon = {
                Text(
                    text = when (exerciseDetails.exercise.language) {
                        "Python" -> "🐍"
                        "C++" -> "⚙️"
                        else -> "💻"
                    }
                )
            }
        )
        AssistChip(
            onClick = { },
            label = { Text(exerciseDetails.exercise.level) },
            colors = AssistChipDefaults.assistChipColors(
                containerColor = when (exerciseDetails.exercise.level) {
                    "Básico" -> MaterialTheme.colorScheme.tertiaryContainer
                    "Intermedio" -> MaterialTheme.colorScheme.secondaryContainer
                    "Avanzado" -> MaterialTheme.colorScheme.errorContainer
                    else -> MaterialTheme.colorScheme.surfaceVariant
                }
            )
        )

        if (exerciseDetails.progress?.status == ProgressStatus.COMPLETED) {
            Spacer(modifier = Modifier.weight(1f))
            AssistChip(
                onClick = { },
                label = { Text("Completado") },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.CheckCircle,
                        contentDescription = null,
                        modifier = Modifier.size(18.dp)
                    )
                },
                colors = AssistChipDefaults.assistChipColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    labelColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    leadingIconContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        }
    }
}

@Composable
private fun ExerciseDescriptionCard(description: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Assignment,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onPrimaryContainer
                )
                Text(
                    text = "Descripción",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = description,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
        }
    }
}

@Composable
private fun HintCard(
    hint: String,
    showHint: Boolean,
    onToggleHint: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Lightbulb,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.secondary
                    )
                    Text(
                        text = "Pista",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                }
                IconButton(onClick = onToggleHint) {
                    Icon(
                        imageVector = if (showHint) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                        contentDescription = if (showHint) "Ocultar pista" else "Mostrar pista"
                    )
                }
            }

            AnimatedVisibility(visible = showHint) {
                Column {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = hint,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}

@Composable
private fun CodeEditorCard(
    userSolution: String,
    language: String,
    onSolutionChange: (String) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Code,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = "Tu solución",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = userSolution,
                onValueChange = onSolutionChange,
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 200.dp, max = 400.dp),
                placeholder = { Text("Escribe tu código aquí...") },
                textStyle = MaterialTheme.typography.bodyMedium.copy(
                    fontFamily = FontFamily.Monospace
                ),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                    focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant
                )
            )

            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Nota: Este editor no ejecuta código. Es solo para práctica.",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
private fun SolutionCard(
    solution: String,
    showSolution: Boolean,
    onToggleSolution: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.tertiaryContainer
        )
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
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Visibility,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onTertiaryContainer
                    )
                    Text(
                        text = "Solución sugerida",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onTertiaryContainer
                    )
                }
                IconButton(onClick = onToggleSolution) {
                    Icon(
                        imageVector = if (showSolution) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                        contentDescription = if (showSolution) "Ocultar solución" else "Mostrar solución",
                        tint = MaterialTheme.colorScheme.onTertiaryContainer
                    )
                }
            }

            AnimatedVisibility(visible = showSolution) {
                Column {
                    Spacer(modifier = Modifier.height(12.dp))
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(8.dp))
                            .background(MaterialTheme.colorScheme.surface)
                            .padding(12.dp)
                    ) {
                        Text(
                            text = solution,
                            style = MaterialTheme.typography.bodyMedium.copy(
                                fontFamily = FontFamily.Monospace
                            ),
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            }
        }
    }
}
