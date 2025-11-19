package com.CodeTrainer.codetrainer.ui.features.exerciselist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.CodeTrainer.codetrainer.domain.model.ExerciseDetails
import com.CodeTrainer.codetrainer.domain.usecase.GetExercisesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ExerciseListUiState(
    val isLoading: Boolean = true,
    val exercises: List<ExerciseDetails> = emptyList(),
    val filteredExercises: List<ExerciseDetails> = emptyList(),
    val error: String? = null,
    val selectedLanguage: String = "Todos",
    val selectedLevel: String = "Todos"
)

@HiltViewModel
class ExerciseListViewModel @Inject constructor(
    private val getExercisesUseCase: GetExercisesUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(ExerciseListUiState())
    val uiState: StateFlow<ExerciseListUiState> = _uiState.asStateFlow()

    init {
        loadExercises()
    }

    private fun loadExercises() {
        viewModelScope.launch {
            getExercisesUseCase()
                .catch { e ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        error = e.message ?: "Error desconocido"
                    )
                }
                .collect { exercises ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        exercises = exercises,
                        filteredExercises = exercises,
                        error = null
                    )
                    // Aplicar filtros actuales si existen
                    applyFilters()
                }
        }
    }

    fun setLanguageFilter(language: String) {
        _uiState.value = _uiState.value.copy(selectedLanguage = language)
        applyFilters()
    }

    fun setLevelFilter(level: String) {
        _uiState.value = _uiState.value.copy(selectedLevel = level)
        applyFilters()
    }

    fun clearFilters() {
        _uiState.value = _uiState.value.copy(
            selectedLanguage = "Todos",
            selectedLevel = "Todos",
            filteredExercises = _uiState.value.exercises
        )
    }

    private fun applyFilters() {
        val currentState = _uiState.value
        var filtered = currentState.exercises

        // Filtrar por lenguaje
        if (currentState.selectedLanguage != "Todos") {
            filtered = filtered.filter {
                it.exercise.language == currentState.selectedLanguage
            }
        }

        // Filtrar por nivel
        if (currentState.selectedLevel != "Todos") {
            filtered = filtered.filter {
                it.exercise.level == currentState.selectedLevel
            }
        }

        _uiState.value = currentState.copy(filteredExercises = filtered)
    }
}