package com.CodeTrainer.codetrainer.ui.features.help

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.CodeTrainer.codetrainer.domain.usecase.GetHelpTopicByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HelpDetailViewModel @Inject constructor(
    private val getHelpTopicByIdUseCase: GetHelpTopicByIdUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val topicId: Int = savedStateHandle.get<Int>("topicId") ?: 0

    private val _uiState = MutableStateFlow(HelpDetailUiState())
    val uiState: StateFlow<HelpDetailUiState> = _uiState.asStateFlow()

    init {
        loadTopicDetail()
    }

    private fun loadTopicDetail() {
        viewModelScope.launch {
            getHelpTopicByIdUseCase(topicId)
                .catch { e ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        error = e.message ?: "Error al cargar el topic"
                    )
                }
                .collect { topic ->
                    if (topic != null) {
                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            topic = topic,
                            error = null
                        )
                    } else {
                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            error = "Topic no encontrado"
                        )
                    }
                }
        }
    }
}
