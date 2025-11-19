package com.CodeTrainer.codetrainer.domain.usecase

import com.CodeTrainer.codetrainer.domain.repository.PreferencesRepository
import javax.inject.Inject

class UpdateDailyRemindersUseCase @Inject constructor(
    private val repository: PreferencesRepository
) {
    suspend operator fun invoke(enabled: Boolean) {
        repository.updateDailyReminders(enabled)
    }
}
