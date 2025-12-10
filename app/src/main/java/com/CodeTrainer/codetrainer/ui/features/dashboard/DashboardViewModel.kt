package com.CodeTrainer.codetrainer.ui.features.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.CodeTrainer.codetrainer.domain.usecase.GetCurrentUserUseCase
import com.CodeTrainer.codetrainer.domain.usecase.GetDailyTipUseCase
import com.CodeTrainer.codetrainer.domain.usecase.GetRandomQuoteUseCase
import com.CodeTrainer.codetrainer.domain.usecase.GetRecentExercisesUseCase
import com.CodeTrainer.codetrainer.domain.usecase.GetStatsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val getCurrentUserUseCase: GetCurrentUserUseCase,
    private val getStatsUseCase: GetStatsUseCase,
    private val getRecentExercisesUseCase: GetRecentExercisesUseCase,
    private val getDailyTipUseCase: GetDailyTipUseCase,
    private val getRandomQuoteUseCase: GetRandomQuoteUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(DashboardUiState())
    val uiState: StateFlow<DashboardUiState> = _uiState.asStateFlow()

    init {
        loadDashboardData()
        loadDailyQuote()
    }

    private fun loadDashboardData() {
        viewModelScope.launch {
            try {
                val currentUser = getCurrentUserUseCase()
                val userName = currentUser?.email?.substringBefore("@") ?: "Usuario"

                combine(
                    getStatsUseCase(),
                    getRecentExercisesUseCase(limit = 5),
                    getDailyTipUseCase()
                ) { stats, recentExercises, dailyTip ->
                    DashboardUiState(
                        isLoading = false,
                        userName = userName,
                        stats = stats,
                        recentExercises = recentExercises,
                        dailyTip = dailyTip,
                        dailyQuote = _uiState.value.dailyQuote,
                        isLoadingQuote = _uiState.value.isLoadingQuote,
                        quoteError = _uiState.value.quoteError,
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

    private fun loadDailyQuote() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoadingQuote = true, quoteError = null)

            val result = getRandomQuoteUseCase()

            result.onSuccess { quote ->
                _uiState.value = _uiState.value.copy(
                    dailyQuote = quote,
                    isLoadingQuote = false,
                    quoteError = null
                )
            }.onFailure { exception ->
                _uiState.value = _uiState.value.copy(
                    dailyQuote = null,
                    isLoadingQuote = false,
                    quoteError = exception.message ?: "Error al cargar quote"
                )
            }
        }
    }

    fun refreshDashboard() {
        _uiState.value = _uiState.value.copy(isLoading = true)
        loadDashboardData()
    }

    fun refreshQuote() {
        loadDailyQuote()
    }
}