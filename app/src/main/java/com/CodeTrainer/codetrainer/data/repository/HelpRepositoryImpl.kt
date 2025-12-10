package com.CodeTrainer.codetrainer.data.repository

import com.CodeTrainer.codetrainer.domain.repository.HelpRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HelpRepositoryImpl @Inject constructor(
    // Inyecta HelpTopicDao u otras fuentes cuando las tengas
) : HelpRepository
