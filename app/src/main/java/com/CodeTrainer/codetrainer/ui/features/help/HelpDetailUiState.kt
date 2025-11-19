package com.CodeTrainer.codetrainer.ui.features.help

import com.CodeTrainer.codetrainer.domain.model.HelpTopic

data class HelpDetailUiState(
    val isLoading: Boolean = true,
    val topic: HelpTopic? = null,
    val error: String? = null
)
