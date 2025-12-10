package com.CodeTrainer.codetrainer.data.repository

import com.CodeTrainer.codetrainer.data.remote.QuoteApiService
import com.CodeTrainer.codetrainer.domain.model.Quote
import com.CodeTrainer.codetrainer.domain.repository.QuoteRepository
import javax.inject.Inject

class QuoteRepositoryImpl @Inject constructor(
    private val apiService: QuoteApiService
) : QuoteRepository {

    override suspend fun getRandomQuote(): Result<Quote> {
        return try {
            val response = apiService.getRandomQuote()
            if (response.isNotEmpty()) {
                val dto = response.first()
                Result.success(Quote(text = dto.quote, author = dto.author))
            } else {
                Result.failure(Exception("No quote received"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}