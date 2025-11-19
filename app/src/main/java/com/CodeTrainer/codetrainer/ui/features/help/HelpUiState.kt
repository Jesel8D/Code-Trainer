package com.CodeTrainer.codetrainer.ui.features.help

import com.CodeTrainer.codetrainer.domain.model.HelpTopic

data class HelpUiState(
    val isLoading: Boolean = true,
    val topics: List<HelpTopic> = emptyList(),
    val topicsByCategory: Map<String, List<HelpTopic>> = emptyMap(),
    val error: String? = null
)
