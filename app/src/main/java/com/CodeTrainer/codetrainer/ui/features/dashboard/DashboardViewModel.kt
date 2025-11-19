package com.CodeTrainer.codetrainer.ui.features.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.CodeTrainer.codetrainer.domain.model.ProgressStatus
import com.CodeTrainer.codetrainer.domain.usecase.GetCurrentUserUseCase
import com.CodeTrainer.codetrainer.domain.usecase.GetExercisesUseCase
import com.CodeTrainer.codetrainer.domain.usecase.GetRandomTipUseCase
import com.CodeTrainer.codetrainer.domain.usecase.GetStatsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import java.util.Calendar
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val getCurrentUserUseCase: GetCurrentUserUseCase,
    private val getStatsUseCase: GetStatsUseCase,
    private val getExercisesUseCase: GetExercisesUseCase,
    private val getRandomTipUseCase: GetRandomTipUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(DashboardUiState())
    val uiState: StateFlow<DashboardUiState> = _uiState.asStateFlow()

    init {
        loadDashboardData()
    }

    private fun loadDashboardData() {
        viewModelScope.launch {
            try {
                // Obtener usuario actual
                val currentUser = getCurrentUserUseCase()
                val userName = currentUser?.email?.substringBefore("@") ?: "Usuario"
                val userEmail = currentUser?.email ?: ""

                // Obtener tip aleatorio
                val tip = getRandomTipUseCase()

                // Combinar flows de stats y ejercicios
                combine(
                    getStatsUseCase(),
                    getExercisesUseCase()
                ) { stats, exercises ->
                    // Filtrar ejercicios pendientes (máximo 5)
                    val pending = exercises
                        .filter { it.progress?.status != ProgressStatus.COMPLETED }
                        .take(5)

                    // Calcular ejercicios completados esta semana
                    val weeklyProgress = calculateWeeklyProgress(exercises)

                    DashboardUiState(
                        isLoading = false,
                        userName = userName,
                        userEmail = userEmail,
                        stats = stats,
                        weeklyProgress = weeklyProgress,
                        pendingExercises = pending,
                        dailyTip = tip,
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

    private fun calculateWeeklyProgress(exercises: List<com.CodeTrainer.codetrainer.domain.model.ExerciseDetails>): Int {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.DAY_OF_WEEK, calendar.firstDayOfWeek)
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)
        val startOfWeek = calendar.timeInMillis

        return exercises.count { exerciseDetail ->
            val progress = exerciseDetail.progress
            progress?.status == ProgressStatus.COMPLETED &&
                    (progress.completedAt ?: 0) >= startOfWeek
        }
    }

    fun refreshDashboard() {
        _uiState.value = _uiState.value.copy(isLoading = true)
        loadDashboardData()
    }
}
