package com.CodeTrainer.codetrainer.ui.features.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.CodeTrainer.codetrainer.domain.repository.HelpRepository
import com.CodeTrainer.codetrainer.domain.usecase.GetUserPreferencesUseCase
import com.CodeTrainer.codetrainer.domain.usecase.UpdateDailyRemindersUseCase
import com.CodeTrainer.codetrainer.domain.usecase.UpdateDarkModeUseCase
import com.CodeTrainer.codetrainer.domain.usecase.UpdateLanguageUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val getUserPreferencesUseCase: GetUserPreferencesUseCase,
    private val updateDarkModeUseCase: UpdateDarkModeUseCase,
    private val updateLanguageUseCase: UpdateLanguageUseCase,
    private val updateDailyRemindersUseCase: UpdateDailyRemindersUseCase,
    private val helpRepository: HelpRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(SettingsUiState())
    val uiState: StateFlow<SettingsUiState> = _uiState.asStateFlow()

    init {
        loadSettings()
    }

    private fun loadSettings() {
        viewModelScope.launch {
            try {
                // Cargar preferencias
                getUserPreferencesUseCase()
                    .catch { e ->
                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            error = e.message ?: "Error al cargar preferencias"
                        )
                    }
                    .collect { preferences ->
                        // Obtener count de help topics
                        val helpCount = helpRepository.getHelpTopicsCount()

                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            preferences = preferences,
                            helpTopicsCount = helpCount,
                            error = null
                        )
                    }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = e.message ?: "Error desconocido"
                )
            }
        }
    }

    fun toggleDarkMode(enabled: Boolean) {
        viewModelScope.launch {
            try {
                updateDarkModeUseCase(enabled)
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    error = "Error al actualizar modo oscuro: ${e.message}"
                )
            }
        }
    }

    fun updateLanguage(language: String) {
        viewModelScope.launch {
            try {
                updateLanguageUseCase(language)
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    error = "Error al actualizar idioma: ${e.message}"
                )
            }
        }
    }

    fun toggleDailyReminders(enabled: Boolean) {
        viewModelScope.launch {
            try {
                updateDailyRemindersUseCase(enabled)
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    error = "Error al actualizar recordatorios: ${e.message}"
                )
            }
        }
    }

    fun initializeHelpData() {
        viewModelScope.launch {
            try {
                _uiState.value = _uiState.value.copy(
                    isInitializingHelp = true,
                    error = null
                )

                val success = helpRepository.initializeHelpData()

                if (success) {
                    val newCount = helpRepository.getHelpTopicsCount()
                    _uiState.value = _uiState.value.copy(
                        isInitializingHelp = false,
                        initializationSuccess = true,
                        helpTopicsCount = newCount
                    )
                } else {
                    _uiState.value = _uiState.value.copy(
                        isInitializingHelp = false,
                        error = "No se pudo inicializar el contenido de ayuda"
                    )
                }

            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isInitializingHelp = false,
                    error = "Error: ${e.message}"
                )
            }
        }
    }

    fun clearSuccessMessage() {
        _uiState.value = _uiState.value.copy(initializationSuccess = false)
    }

    fun clearError() {
        _uiState.value = _uiState.value.copy(error = null)
    }
}
