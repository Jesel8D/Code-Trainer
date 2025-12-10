package com.CodeTrainer.codetrainer.domain.usecase

import com.CodeTrainer.codetrainer.domain.model.Quote
import com.CodeTrainer.codetrainer.domain.repository.QuoteRepository
import javax.inject.Inject

class GetRandomQuoteUseCase @Inject constructor(
    private val repository: QuoteRepository
) {
    suspend operator fun invoke(): Result<Quote> = repository.getRandomQuote()
}