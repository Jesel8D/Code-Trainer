package com.CodeTrainer.codetrainer.domain.repository

import com.CodeTrainer.codetrainer.domain.model.Quote

interface QuoteRepository {
    suspend fun getRandomQuote(): Result<Quote>
}