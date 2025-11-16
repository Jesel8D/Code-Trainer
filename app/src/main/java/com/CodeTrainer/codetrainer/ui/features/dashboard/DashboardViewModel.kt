package com.CodeTrainer.codetrainer.ui.features.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.CodeTrainer.codetrainer.domain.model.AuthUser
import com.CodeTrainer.codetrainer.domain.model.ExerciseDetails
import com.CodeTrainer.codetrainer.domain.model.Stats
import com.CodeTrainer.codetrainer.domain.usecase.GetCurrentUserUseCase
import com.CodeTrainer.codetrainer.domain.usecase.GetPendingExercisesUseCase
import com.CodeTrainer.codetrainer.domain.usecase.GetStatsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

// 1. El estado que describe la UI del Dashboard
data class DashboardUiState(
    val isLoading: Boolean = true,
    val user: AuthUser? = null,
    val stats: Stats? = null,
    val pendingExercises: List<ExerciseDetails> = emptyList()
)

@HiltViewModel
class DashboardViewModel @Inject constructor(
    // 2. Inyectamos TODOS los casos de uso que necesitamos
    getCurrentUserUseCase: GetCurrentUserUseCase,
    getStatsUseCase: GetStatsUseCase,
    getPendingExercisesUseCase: GetPendingExercisesUseCase
) : ViewModel() {

    // 3. Usamos "combine" para unir los 3 Flujos de datos en 1 solo UiState
    val uiState: StateFlow<DashboardUiState> = combine(
        getStatsUseCase(),
        getPendingExercisesUseCase()
    ) { stats, pendingExercises ->
        // 4. Cada vez que 'stats' O 'pendingExercises' cambien,
        //    se creará un nuevo estado
        DashboardUiState(
            isLoading = false,
            user = getCurrentUserUseCase(), // Obtenemos el usuario actual
            stats = stats,
            pendingExercises = pendingExercises
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = DashboardUiState(isLoading = true) // Estado inicial
    )

    // NOTA: 'getCurrentUserUseCase' no es un Flow, así que lo llamamos
    // directamente dentro del 'combine'.
}