package com.CodeTrainer.codetrainer.domain.usecase

import android.net.Uri
import com.CodeTrainer.codetrainer.domain.repository.ProfileImageRepository
import javax.inject.Inject

class UploadProfileImageUseCase @Inject constructor(
    private val repository: ProfileImageRepository
) {
    suspend operator fun invoke(userId: String, imageUri: Uri): Result<String> {
        return repository.uploadProfileImage(userId, imageUri)
    }
}