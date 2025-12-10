package com.CodeTrainer.codetrainer.data.remote

import com.CodeTrainer.codetrainer.data.remote.dto.QuoteDto
import retrofit2.http.GET

interface QuoteApiService {
    @GET("random")
    suspend fun getRandomQuote(): List<QuoteDto>
}