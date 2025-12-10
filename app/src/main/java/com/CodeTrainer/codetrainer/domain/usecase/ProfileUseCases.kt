package com.CodeTrainer.codetrainer.domain.usecase

import android.net.Uri
import com.CodeTrainer.codetrainer.domain.model.Stats
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetProfileImageUseCase @Inject constructor(
    // Inyecta ProfileImageRepository cuando lo tengas
    // private val profileImageRepository: ProfileImageRepository
) {
    operator fun invoke(): Flow<String?> = flow {
        // URL o path local de la imagen (por ahora null)
        emit(null)
    }
}

class UploadProfileImageUseCase @Inject constructor(
    // Inyecta ProfileImageRepository cuando lo tengas
    // private val profileImageRepository: ProfileImageRepository
) {
    suspend operator fun invoke(imageUri: Uri): Result<Unit> {
        // Implementa el upload real después
        return Result.success(Unit)
    }
}

class LogoutUseCase @Inject constructor(
    // Inyecta AuthRepository cuando lo tengas
    // private val authRepository: AuthRepository
) {
    suspend operator fun invoke() {
        // Implementa el logout real después
    }
}

class GetStatsUseCase @Inject constructor(
    // Inyecta StatsRepository cuando lo tengas
    // private val statsRepository: StatsRepository
) {
    operator fun invoke(): Flow<Stats> = flow {
        // Stats "vacías" para no romper nada
        emit(
            Stats(
                totalExercisesCompleted = 0,
                totalTimeSpent = 0L,
                averageScore = 0.0,
                dailyStreak = 0
            )
        )
    }
}
