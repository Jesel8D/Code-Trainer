package com.CodeTrainer.codetrainer.ui.features.profile

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.CodeTrainer.codetrainer.domain.model.ProgressStatus
import com.CodeTrainer.codetrainer.domain.usecase.GetCurrentUserUseCase
import com.CodeTrainer.codetrainer.domain.usecase.GetExercisesUseCase
import com.CodeTrainer.codetrainer.domain.usecase.GetProfileImageUseCase
import com.CodeTrainer.codetrainer.domain.usecase.GetStatsUseCase
import com.CodeTrainer.codetrainer.domain.usecase.LogoutUseCase
import com.CodeTrainer.codetrainer.domain.usecase.UploadProfileImageUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getCurrentUserUseCase: GetCurrentUserUseCase,
    private val getStatsUseCase: GetStatsUseCase,
    private val getExercisesUseCase: GetExercisesUseCase,
    private val logoutUseCase: LogoutUseCase,
    private val getProfileImageUseCase: GetProfileImageUseCase,
    private val uploadProfileImageUseCase: UploadProfileImageUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState: StateFlow<ProfileUiState> = _uiState.asStateFlow()

    init {
        loadProfileData()
    }

    private fun loadProfileData() {
        viewModelScope.launch {
            try {
                val currentUser = getCurrentUserUseCase()
                val userName = currentUser?.email?.substringBefore("@") ?: "Usuario"
                val userEmail = currentUser?.email ?: ""
                val userId = currentUser?.uid ?: ""

                // Cargar imagen de perfil
                val profileImageResult = getProfileImageUseCase(userId)
                val profileImageUrl = profileImageResult.getOrNull()

                combine(
                    getStatsUseCase(),
                    getExercisesUseCase()
                ) { stats, exercises ->
                    val completedExercises = exercises.filter {
                        it.progress?.status == ProgressStatus.COMPLETED
                    }

                    val pythonCompleted = completedExercises.count {
                        it.exercise.language == "Python"
                    }
                    val cppCompleted = completedExercises.count {
                        it.exercise.language == "C++"
                    }

                    val mostPracticed = when {
                        pythonCompleted > cppCompleted -> "Python 🐍"
                        cppCompleted > pythonCompleted -> "C++ ⚙️"
                        pythonCompleted == 0 && cppCompleted == 0 -> "Ninguno"
                        else -> "Ambos 💻"
                    }

                    val basicCompleted = completedExercises.count {
                        it.exercise.level == "Básico"
                    }
                    val intermediateCompleted = completedExercises.count {
                        it.exercise.level == "Intermedio"
                    }
                    val advancedCompleted = completedExercises.count {
                        it.exercise.level == "Avanzado"
                    }

                    ProfileUiState(
                        isLoading = false,
                        userName = userName,
                        userEmail = userEmail,
                        userId = userId,
                        profileImageUrl = profileImageUrl,
                        stats = stats,
                        mostPracticedLanguage = mostPracticed,
                        pythonCompleted = pythonCompleted,
                        cppCompleted = cppCompleted,
                        basicCompleted = basicCompleted,
                        intermediateCompleted = intermediateCompleted,
                        advancedCompleted = advancedCompleted,
                        error = null
                    )
                }
                    .catch { e ->
                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            error = e.message ?: "Error desconocido"
                        )
                    }
                    .collect { newState ->
                        _uiState.value = newState
                    }

            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = e.message ?: "Error al cargar datos"
                )
            }
        }
    }

    fun uploadProfileImage(imageUri: Uri) {
        viewModelScope.launch {
            try {
                _uiState.value = _uiState.value.copy(isUploadingImage = true, error = null)

                val userId = _uiState.value.userId
                val result = uploadProfileImageUseCase(userId, imageUri)

                result.onSuccess { downloadUrl ->
                    _uiState.value = _uiState.value.copy(
                        isUploadingImage = false,
                        profileImageUrl = downloadUrl
                    )
                }.onFailure { exception ->
                    _uiState.value = _uiState.value.copy(
                        isUploadingImage = false,
                        error = "Error al subir imagen: ${exception.message}"
                    )
                }

            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isUploadingImage = false,
                    error = "Error: ${e.message}"
                )
            }
        }
    }

    fun logout(onLogoutComplete: () -> Unit) {
        viewModelScope.launch {
            try {
                _uiState.value = _uiState.value.copy(isLoggingOut = true)
                logoutUseCase()
                onLogoutComplete()
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoggingOut = false,
                    error = e.message ?: "Error al cerrar sesión"
                )
            }
        }
    }

    fun refreshProfile() {
        _uiState.value = _uiState.value.copy(isLoading = true)
        loadProfileData()
    }
}