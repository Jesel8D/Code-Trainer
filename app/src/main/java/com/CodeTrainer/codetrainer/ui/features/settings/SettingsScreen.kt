package com.CodeTrainer.codetrainer.ui.features.settings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    uiState: SettingsUiState,
    onNavigateToHelp: () -> Unit,
    onDarkModeToggle: (Boolean) -> Unit,
    onLanguageChange: (String) -> Unit,
    onDailyRemindersToggle: (Boolean) -> Unit,
    onInitializeHelp: () -> Unit,
    onClearError: () -> Unit,
    onClearSuccess: () -> Unit
) {
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
    LaunchedEffect(uiState.initializationSuccess) {
        if (uiState.initializationSuccess) {
            snackbarHostState.showSnackbar(
                message = "Contenido de ayuda inicializado correctamente",
                duration = SnackbarDuration.Short
            )
            onClearSuccess()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Ajustes") }
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
            else -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .verticalScroll(rememberScrollState())
                ) {
                    // Sección: Apariencia
                    SettingsSection(title = "Apariencia")

                    SettingsSwitchItem(
                        icon = Icons.Default.DarkMode,
                        title = "Modo oscuro",
                        subtitle = "Activa el tema oscuro de la aplicación",
                        checked = uiState.preferences.isDarkMode,
                        onCheckedChange = onDarkModeToggle
                    )

                    Divider(modifier = Modifier.padding(horizontal = 16.dp))

                    // Sección: Idioma
                    SettingsSection(title = "Idioma")

                    SettingsClickableItem(
                        icon = Icons.Default.Language,
                        title = "Idioma de la app",
                        subtitle = uiState.preferences.language.uppercase(),
                        onClick = { /* Por ahora solo español */ }
                    )

                    Text(
                        text = "Actualmente solo disponible en Español",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(horizontal = 72.dp, vertical = 4.dp)
                    )

                    Divider(modifier = Modifier.padding(horizontal = 16.dp))

                    // Sección: Notificaciones
                    SettingsSection(title = "Notificaciones")

                    SettingsSwitchItem(
                        icon = Icons.Default.Notifications,
                        title = "Recordatorios diarios",
                        subtitle = "Recibe recordatorios para practicar cada día",
                        checked = uiState.preferences.dailyRemindersEnabled,
                        onCheckedChange = onDailyRemindersToggle
                    )

                    Divider(modifier = Modifier.padding(horizontal = 16.dp))

                    // Sección: Contenido offline
                    SettingsSection(title = "Contenido offline")

                    SettingsClickableItem(
                        icon = Icons.Default.Download,
                        title = "Documentación de ayuda",
                        subtitle = "${uiState.helpTopicsCount} topics disponibles",
                        onClick = onInitializeHelp,
                        showProgress = uiState.isInitializingHelp
                    )

                    Text(
                        text = "Inicializa o actualiza la documentación offline",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(horizontal = 72.dp, vertical = 4.dp)
                    )

                    Divider(modifier = Modifier.padding(horizontal = 16.dp))

                    // Sección: Ayuda
                    SettingsSection(title = "Ayuda y soporte")

                    SettingsClickableItem(
                        icon = Icons.Default.Help,
                        title = "Centro de ayuda",
                        subtitle = "Guías, tutoriales y preguntas frecuentes",
                        onClick = onNavigateToHelp
                    )

                    SettingsClickableItem(
                        icon = Icons.Default.Info,
                        title = "Acerca de",
                        subtitle = "CodeTrainer v1.0",
                        onClick = { /* TODO: Mostrar info de la app */ }
                    )

                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}

@Composable
private fun SettingsSection(title: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.titleSmall,
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colorScheme.primary,
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)
    )
}

@Composable
private fun SettingsSwitchItem(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    title: String,
    subtitle: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onCheckedChange(!checked) }
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier.size(24.dp),
            tint = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.width(16.dp))

        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Medium
            )
            Text(
                text = subtitle,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange
        )
    }
}

@Composable
private fun SettingsClickableItem(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    title: String,
    subtitle: String,
    onClick: () -> Unit,
    showProgress: Boolean = false
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(enabled = !showProgress, onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier.size(24.dp),
            tint = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.width(16.dp))

        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Medium
            )
            Text(
                text = subtitle,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        if (showProgress) {
            CircularProgressIndicator(
                modifier = Modifier.size(24.dp),
                strokeWidth = 2.dp
            )
        } else {
            Icon(
                imageVector = Icons.Default.ChevronRight,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}
