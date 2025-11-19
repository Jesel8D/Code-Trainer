package com.CodeTrainer.codetrainer.ui.features.exercisedetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.CodeTrainer.codetrainer.domain.model.Progress
import com.CodeTrainer.codetrainer.domain.model.ProgressStatus
import com.CodeTrainer.codetrainer.domain.usecase.GetExerciseDetailsUseCase
import com.CodeTrainer.codetrainer.domain.usecase.SaveProgressUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExerciseDetailViewModel @Inject constructor(
    private val getExerciseDetailsUseCase: GetExerciseDetailsUseCase,
    private val saveProgressUseCase: SaveProgressUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val exerciseId: Int = savedStateHandle.get<Int>("exerciseId") ?: 0

    private val _uiState = MutableStateFlow(ExerciseDetailUiState())
    val uiState: StateFlow<ExerciseDetailUiState> = _uiState.asStateFlow()

    init {
        loadExerciseDetails()
    }

    private fun loadExerciseDetails() {
        viewModelScope.launch {
            getExerciseDetailsUseCase(exerciseId)
                .catch { e ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        error = e.message ?: "Error al cargar el ejercicio"
                    )
                }
                .collect { details ->
                    if (details != null) {
                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            exerciseDetails = details,
                            userSolution = details.progress?.userSolution ?: "",
                            error = null
                        )
                    } else {
                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            error = "Ejercicio no encontrado"
                        )
                    }
                }
        }
    }

    fun onSolutionChange(newSolution: String) {
        _uiState.value = _uiState.value.copy(
            userSolution = newSolution,
            saveSuccess = false
        )
    }

    fun toggleHint() {
        _uiState.value = _uiState.value.copy(
            showHint = !_uiState.value.showHint
        )
    }

    fun toggleSolution() {
        _uiState.value = _uiState.value.copy(
            showSolution = !_uiState.value.showSolution
        )
    }

    fun submitSolution() {
        val currentState = _uiState.value
        val details = currentState.exerciseDetails ?: return

        if (currentState.userSolution.isBlank()) {
            _uiState.value = currentState.copy(
                error = "Por favor escribe una solución antes de enviar"
            )
            return
        }

        viewModelScope.launch {
            try {
                _uiState.value = currentState.copy(isSaving = true, error = null)

                val progress = Progress(
                    exerciseId = details.exercise.id,
                    status = ProgressStatus.COMPLETED,
                    completedAt = System.currentTimeMillis(),
                    score = 100.0, // Por simplicidad, asignamos 100
                    userSolution = currentState.userSolution
                )

                saveProgressUseCase(progress)

                _uiState.value = _uiState.value.copy(
                    isSaving = false,
                    saveSuccess = true
                )

                // Recargar detalles para actualizar el estado
                loadExerciseDetails()

            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isSaving = false,
                    error = e.message ?: "Error al guardar la solución"
                )
            }
        }
    }

    fun clearError() {
        _uiState.value = _uiState.value.copy(error = null)
    }

    fun clearSuccessMessage() {
        _uiState.value = _uiState.value.copy(saveSuccess = false)
    }
}
