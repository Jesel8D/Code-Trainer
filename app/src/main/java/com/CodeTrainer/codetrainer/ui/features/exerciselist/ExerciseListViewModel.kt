package com.CodeTrainer.codetrainer.ui.features.exerciselist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.CodeTrainer.codetrainer.domain.model.ExerciseDetails
import com.CodeTrainer.codetrainer.domain.usecase.GetExercisesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

// 1. Anotamos con @HiltViewModel para que Hilt sepa cómo crearlo
@HiltViewModel
class ExerciseListViewModel @Inject constructor(
    // 2. Inyectamos nuestro Caso de Uso
    getExercisesUseCase: GetExercisesUseCase
) : ViewModel() {

    // 3. Definimos un "Estado de UI" (UiState)
    //    Esto representa todo lo que la pantalla puede necesitar saber.
    val uiState: StateFlow<ExerciseListUiState> =
        // 4. Llamamos al Caso de Uso (que devuelve un Flow)
        getExercisesUseCase()
            .map { exercises ->
                // 5. Transformamos la lista de datos en un estado de UI
                ExerciseListUiState.Success(exercises)
            }
            // 6. Convertimos el "Flow" frío en un "StateFlow" caliente.
            //    Esto "guarda" el último estado y lo comparte con la UI.
            .stateIn(
                scope = viewModelScope, // El scope del ViewModel
                started = SharingStarted.WhileSubscribed(5000L), // Cómo/cuándo empezar a "escuchar"
                initialValue = ExerciseListUiState.Loading // El estado inicial
            )
}

// 7. Definimos los posibles estados de nuestra pantalla
sealed interface ExerciseListUiState {
    data object Loading : ExerciseListUiState
    data class Success(val exercises: List<ExerciseDetails>) : ExerciseListUiState
    data class Error(val message: String) : ExerciseListUiState
}