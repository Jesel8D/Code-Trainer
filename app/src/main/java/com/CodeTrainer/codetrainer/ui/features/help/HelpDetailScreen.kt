package com.CodeTrainer.codetrainer.ui.features.help

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.CodeTrainer.codetrainer.domain.model.HelpTopic

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HelpDetailScreen(
    uiState: HelpDetailUiState,
    onNavigateBack: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = uiState.topic?.title ?: "Ayuda",
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
            uiState.error != null || uiState.topic == null -> {
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
                            text = uiState.error ?: "Topic no encontrado",
                            color = MaterialTheme.colorScheme.error
                        )
                        Button(onClick = onNavigateBack) {
                            Text("Volver")
                        }
                    }
                }
            }
            else -> {
                HelpDetailContent(
                    modifier = Modifier.padding(paddingValues),
                    topic = uiState.topic
                )
            }
        }
    }
}

@Composable
private fun HelpDetailContent(
    modifier: Modifier = Modifier,
    topic: HelpTopic
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Renderizar el contenido markdown (simplificado)
        MarkdownContent(content = topic.content)

        Spacer(modifier = Modifier.height(32.dp))
    }
}

@Composable
private fun MarkdownContent(content: String) {
    // Parser simple de Markdown
    val lines = content.split("\n")

    lines.forEach { line ->
        when {
            // Títulos H1
            line.startsWith("# ") -> {
                Text(
                    text = line.removePrefix("# "),
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }
            // Títulos H2
            line.startsWith("## ") -> {
                Text(
                    text = line.removePrefix("## "),
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(vertical = 6.dp)
                )
            }
            // Títulos H3
            line.startsWith("### ") -> {
                Text(
                    text = line.removePrefix("### "),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(vertical = 4.dp)
                )
            }
            // Bloques de código
            line.startsWith("```") -> {
                // Ignorar las líneas de inicio/fin de bloque de código
            }
            // Lista con viñetas
            line.trim().startsWith("- ") -> {
                Row(
                    modifier = Modifier.padding(vertical = 2.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "•",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = line.trim().removePrefix("- "),
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
            // Lista numerada
            line.trim().matches(Regex("^\\d+\\.\\s.*")) -> {
                Text(
                    text = line.trim(),
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(vertical = 2.dp)
                )
            }
            // Checkmarks
            line.trim().startsWith("✅") || line.trim().startsWith("📡") -> {
                Text(
                    text = line.trim(),
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(vertical = 2.dp)
                )
            }
            // Línea vacía
            line.isBlank() -> {
                Spacer(modifier = Modifier.height(8.dp))
            }
            // Código inline (detectar si está entre bloques de código)
            line.trim().startsWith("    ") || line.contains("def ") || line.contains("return ") -> {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(8.dp))
                        .background(MaterialTheme.colorScheme.surfaceVariant)
                        .padding(12.dp)
                ) {
                    Text(
                        text = line.trim(),
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontFamily = FontFamily.Monospace
                        ),
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
            // Texto normal
            else -> {
                if (line.isNotBlank()) {
                    Text(
                        text = line,
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(vertical = 2.dp)
                    )
                }
            }
        }
    }
}
