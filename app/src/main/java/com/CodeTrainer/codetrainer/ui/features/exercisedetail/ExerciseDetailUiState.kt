package com.CodeTrainer.codetrainer.ui.features.exercisedetail

import com.CodeTrainer.codetrainer.domain.model.ExerciseDetails

data class ExerciseDetailUiState(
    val isLoading: Boolean = true,
    val exerciseDetails: ExerciseDetails? = null,
    val userSolution: String = "",
    val showHint: Boolean = false,
    val showSolution: Boolean = false,
    val isSaving: Boolean = false,
    val saveSuccess: Boolean = false,
    val error: String? = null
)
