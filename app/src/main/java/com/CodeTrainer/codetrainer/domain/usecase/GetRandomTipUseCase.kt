package com.CodeTrainer.codetrainer.domain.usecase

import com.CodeTrainer.codetrainer.domain.model.Tip
import com.CodeTrainer.codetrainer.domain.repository.TipRepository
import javax.inject.Inject

class GetRandomTipUseCase @Inject constructor(
    private val repository: TipRepository
) {
    suspend operator fun invoke(): Tip? = repository.getRandomTip()
}
