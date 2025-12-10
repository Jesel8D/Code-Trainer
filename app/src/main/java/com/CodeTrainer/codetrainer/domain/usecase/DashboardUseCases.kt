package com.CodeTrainer.codetrainer.domain.usecase

import com.CodeTrainer.codetrainer.domain.model.ExerciseWithProgress
import com.CodeTrainer.codetrainer.domain.model.Quote
import com.CodeTrainer.codetrainer.domain.model.Tip
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 * Use case para obtener ejercicios recientes con su progreso.
 * De momento devuelve una lista vacía para no romper compilación;
 * más adelante lo puedes conectar a tu ExerciseRepository.
 */
class GetRecentExercisesUseCase @Inject constructor(
    // Inyecta ExerciseRepository cuando quieras usar datos reales
    // private val exerciseRepository: ExerciseRepository
) {
    operator fun invoke(limit: Int): Flow<List<ExerciseWithProgress>> = flow {
        emit(emptyList())
    }
}

/**
 * Use case para obtener el tip diario.
 * De momento devuelve null.
 */
class GetDailyTipUseCase @Inject constructor(
    // Inyecta TipRepository cuando lo tengas
    // private val tipRepository: TipRepository
) {
    operator fun invoke(): Flow<Tip?> = flow {
        emit(null)
    }
}

/**
 * Use case para obtener una cita aleatoria.
 * De momento devuelve null.
 */
class GetRandomQuoteUseCase @Inject constructor(
    // Inyecta el repositorio o data source de Quote cuando lo tengas
    // private val quoteRepository: QuoteRepository
) {
    operator fun invoke(): Flow<Quote?> = flow {
        emit(null)
    }
}

