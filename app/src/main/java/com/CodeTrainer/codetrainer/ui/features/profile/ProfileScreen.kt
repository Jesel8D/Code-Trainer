package com.CodeTrainer.codetrainer.ui.features.profile

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.core.content.FileProvider
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale
import java.io.File

@OptIn(ExperimentalMaterial3Api::class, ExperimentalPermissionsApi::class)
@Composable
fun ProfileScreen(
    uiState: ProfileUiState,
    onLogout: () -> Unit,
    onRefresh: () -> Unit,
    onImageSelected: (Uri) -> Unit
) {
    var showLogoutDialog by remember { mutableStateOf(false) }
    var showImagePickerDialog by remember { mutableStateOf(false) }

    val context = LocalContext.current

    // Permisos de cámara
    val cameraPermissionState = rememberPermissionState(
        android.Manifest.permission.CAMERA
    )

    // URI temporal para la foto de cámara
    var tempImageUri by remember { mutableStateOf<Uri?>(null) }

    // Launcher para la cámara
    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture()
    ) { success ->
        if (success && tempImageUri != null) {
            onImageSelected(tempImageUri!!)
        }
    }

    // Launcher para la galería
    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let { onImageSelected(it) }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Perfil") }
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
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Text(
                            text = "Error: ${uiState.error}",
                            color = MaterialTheme.colorScheme.error
                        )
                        Button(onClick = onRefresh) {
                            Text("Reintentar")
                        }
                    }
                }
            }
            else -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .verticalScroll(rememberScrollState())
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // Header con foto de perfil
                    ProfileHeader(
                        userName = uiState.userName,
                        userEmail = uiState.userEmail,
                        profileImageUrl = uiState.profileImageUrl,
                        isUploadingImage = uiState.isUploadingImage,
                        onChangePhotoClick = { showImagePickerDialog = true }
                    )

                    // Estadísticas principales
                    MainStatsCard(uiState = uiState)

                    // Progreso por lenguaje
                    LanguageProgressCard(
                        pythonCompleted = uiState.pythonCompleted,
                        cppCompleted = uiState.cppCompleted
                    )

                    // Progreso por nivel
                    LevelProgressCard(
                        basicCompleted = uiState.basicCompleted,
                        intermediateCompleted = uiState.intermediateCompleted,
                        advancedCompleted = uiState.advancedCompleted
                    )

                    // Botón de cerrar sesión
                    Spacer(modifier = Modifier.height(8.dp))

                    OutlinedButton(
                        onClick = { showLogoutDialog = true },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = MaterialTheme.colorScheme.error
                        )
                    ) {
                        Icon(
                            imageVector = Icons.Default.Logout,
                            contentDescription = null
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Cerrar sesión")
                    }

                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }

    // Diálogo de confirmación de logout
    if (showLogoutDialog) {
        AlertDialog(
            onDismissRequest = { showLogoutDialog = false },
            icon = {
                Icon(
                    imageVector = Icons.Default.Logout,
                    contentDescription = null
                )
            },
            title = { Text("Cerrar sesión") },
            text = { Text("¿Estás seguro de que deseas cerrar sesión?") },
            confirmButton = {
                TextButton(
                    onClick = {
                        showLogoutDialog = false
                        onLogout()
                    },
                    enabled = !uiState.isLoggingOut
                ) {
                    if (uiState.isLoggingOut) {
                        CircularProgressIndicator(modifier = Modifier.size(20.dp))
                    } else {
                        Text("Cerrar sesión")
                    }
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { showLogoutDialog = false },
                    enabled = !uiState.isLoggingOut
                ) {
                    Text("Cancelar")
                }
            }
        )
    }

    // Diálogo selector de imagen
    if (showImagePickerDialog) {
        AlertDialog(
            onDismissRequest = { showImagePickerDialog = false },
            icon = {
                Icon(
                    imageVector = Icons.Default.PhotoCamera,
                    contentDescription = null
                )
            },
            title = { Text("Cambiar foto de perfil") },
            text = {
                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text("Selecciona una opción:")

                    // Opción de cámara
                    OutlinedButton(
                        onClick = {
                            showImagePickerDialog = false
                            if (cameraPermissionState.status.isGranted) {
                                // Crear archivo temporal para la foto
                                val photoFile = File(
                                    context.cacheDir,
                                    "profile_${System.currentTimeMillis()}.jpg"
                                )
                                tempImageUri = FileProvider.getUriForFile(
                                    context,
                                    "${context.packageName}.fileprovider",
                                    photoFile
                                )
                                cameraLauncher.launch(tempImageUri!!)
                            } else {
                                cameraPermissionState.launchPermissionRequest()
                            }
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Icon(Icons.Default.PhotoCamera, contentDescription = null)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Tomar foto")
                    }

                    // Opción de galería
                    OutlinedButton(
                        onClick = {
                            showImagePickerDialog = false
                            galleryLauncher.launch("image/*")
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Icon(Icons.Default.PhotoLibrary, contentDescription = null)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Elegir de galería")
                    }
                }
            },
            confirmButton = {},
            dismissButton = {
                TextButton(onClick = { showImagePickerDialog = false }) {
                    Text("Cancelar")
                }
            }
        )
    }

    // Mostrar diálogo si se necesita explicar el permiso
    if (cameraPermissionState.status.shouldShowRationale) {
        AlertDialog(
            onDismissRequest = {},
            title = { Text("Permiso de cámara") },
            text = { Text("Esta app necesita acceso a la cámara para tomar fotos de perfil.") },
            confirmButton = {
                TextButton(onClick = { cameraPermissionState.launchPermissionRequest() }) {
                    Text("Otorgar permiso")
                }
            },
            dismissButton = {
                TextButton(onClick = {}) {
                    Text("Cancelar")
                }
            }
        )
    }
}

@Composable
private fun ProfileHeader(
    userName: String,
    userEmail: String,
    profileImageUrl: String?,
    isUploadingImage: Boolean,
    onChangePhotoClick: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Foto de perfil
            Box(
                contentAlignment = Alignment.Center
            ) {
                if (profileImageUrl != null) {
                    // Mostrar imagen de perfil desde URL
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(profileImageUrl)
                            .crossfade(true)
                            .build(),
                        contentDescription = "Foto de perfil",
                        modifier = Modifier
                            .size(120.dp)
                            .clip(CircleShape)
                            .border(
                                width = 3.dp,
                                color = MaterialTheme.colorScheme.primary,
                                shape = CircleShape
                            )
                            .clickable(onClick = onChangePhotoClick),
                        contentScale = ContentScale.Crop
                    )
                } else {
                    // Avatar por defecto con inicial
                    Box(
                        modifier = Modifier
                            .size(120.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.primary)
                            .clickable(onClick = onChangePhotoClick),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = userName.firstOrNull()?.uppercase() ?: "U",
                            style = MaterialTheme.typography.displayLarge,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                }

                // Icono de cámara flotante
                Box(
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .size(36.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.secondary)
                        .clickable(onClick = onChangePhotoClick),
                    contentAlignment = Alignment.Center
                ) {
                    if (isUploadingImage) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(20.dp),
                            color = MaterialTheme.colorScheme.onSecondary,
                            strokeWidth = 2.dp
                        )
                    } else {
                        Icon(
                            imageVector = Icons.Default.PhotoCamera,
                            contentDescription = "Cambiar foto",
                            tint = MaterialTheme.colorScheme.onSecondary,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            }

            // Info del usuario
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = userName,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
                Text(
                    text = userEmail,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
        }
    }
}

@Composable
private fun MainStatsCard(uiState: ProfileUiState) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = "Estadísticas generales",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                StatItemVertical(
                    icon = Icons.Default.CheckCircle,
                    value = uiState.stats.totalExercisesCompleted.toString(),
                    label = "Completados",
                    color = MaterialTheme.colorScheme.primary
                )
                StatItemVertical(
                    icon = Icons.Default.LocalFireDepartment,
                    value = uiState.stats.dailyStreak.toString(),
                    label = "Racha de días",
                    color = MaterialTheme.colorScheme.tertiary
                )
            }

            Spacer(modifier = Modifier.height(16.dp))
            HorizontalDivider()
            Spacer(modifier = Modifier.height(16.dp))

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
                        imageVector = Icons.Default.Code,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.secondary
                    )
                    Text(
                        text = "Lenguaje favorito",
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
                Text(
                    text = uiState.mostPracticedLanguage,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}

@Composable
private fun StatItemVertical(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    value: String,
    label: String,
    color: Color
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier.size(36.dp),
            tint = color
        )
        Text(
            text = value,
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = color
        )
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
private fun LanguageProgressCard(
    pythonCompleted: Int,
    cppCompleted: Int
) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = "Progreso por lenguaje",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )

            LanguageProgressItem(
                language = "Python 🐍",
                completed = pythonCompleted,
                total = 15,
                color = MaterialTheme.colorScheme.primary
            )

            LanguageProgressItem(
                language = "C++ ⚙️",
                completed = cppCompleted,
                total = 15,
                color = MaterialTheme.colorScheme.secondary
            )
        }
    }
}

@Composable
private fun LanguageProgressItem(
    language: String,
    completed: Int,
    total: Int,
    color: Color
) {
    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = language,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.SemiBold
            )
            Text(
                text = "$completed/$total",
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold,
                color = color
            )
        }
        LinearProgressIndicator(
            progress = { if (total > 0) completed.toFloat() / total.toFloat() else 0f },
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp)
                .clip(MaterialTheme.shapes.small),
            color = color,
            trackColor = MaterialTheme.colorScheme.surfaceVariant
        )
    }
}

@Composable
private fun LevelProgressCard(
    basicCompleted: Int,
    intermediateCompleted: Int,
    advancedCompleted: Int
) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = "Progreso por dificultad",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )

            LevelProgressItem(
                level = "Básico",
                completed = basicCompleted,
                total = 10,
                color = MaterialTheme.colorScheme.tertiary
            )

            LevelProgressItem(
                level = "Intermedio",
                completed = intermediateCompleted,
                total = 10,
                color = MaterialTheme.colorScheme.secondary
            )

            LevelProgressItem(
                level = "Avanzado",
                completed = advancedCompleted,
                total = 10,
                color = MaterialTheme.colorScheme.error
            )
        }
    }
}

@Composable
private fun LevelProgressItem(
    level: String,
    completed: Int,
    total: Int,
    color: Color
) {
    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = level,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.SemiBold
            )
            Text(
                text = "$completed/$total",
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold,
                color = color
            )
        }
        LinearProgressIndicator(
            progress = { if (total > 0) completed.toFloat() / total.toFloat() else 0f },
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp)
                .clip(MaterialTheme.shapes.small),
            color = color,
            trackColor = MaterialTheme.colorScheme.surfaceVariant
        )
    }
}