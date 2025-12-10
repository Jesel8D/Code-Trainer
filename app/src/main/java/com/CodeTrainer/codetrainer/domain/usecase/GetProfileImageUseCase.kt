package com.CodeTrainer.codetrainer.domain.usecase

import com.CodeTrainer.codetrainer.domain.repository.ProfileImageRepository
import javax.inject.Inject

class GetProfileImageUseCase @Inject constructor(
    private val repository: ProfileImageRepository
) {
    suspend operator fun invoke(userId: String): Result<String?> {
        return repository.getProfileImageUrl(userId)
    }
}