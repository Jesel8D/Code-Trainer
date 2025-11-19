package com.CodeTrainer.codetrainer.ui.features.help

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.CodeTrainer.codetrainer.domain.usecase.GetAllHelpTopicsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HelpViewModel @Inject constructor(
    private val getAllHelpTopicsUseCase: GetAllHelpTopicsUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(HelpUiState())
    val uiState: StateFlow<HelpUiState> = _uiState.asStateFlow()

    init {
        loadHelpTopics()
    }

    private fun loadHelpTopics() {
        viewModelScope.launch {
            getAllHelpTopicsUseCase()
                .catch { e ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        error = e.message ?: "Error al cargar topics de ayuda"
                    )
                }
                .collect { topics ->
                    // Agrupar por categoría
                    val grouped = topics.groupBy { it.category }

                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        topics = topics,
                        topicsByCategory = grouped,
                        error = null
                    )
                }
        }
    }

    fun refresh() {
        _uiState.value = _uiState.value.copy(isLoading = true)
        loadHelpTopics()
    }
}
