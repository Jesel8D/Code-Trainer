package com.CodeTrainer.codetrainer.ui.features.profile

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun ProfileRoute(
    viewModel: ProfileViewModel = hiltViewModel(),
    onLogout: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    ProfileScreen(
        uiState = uiState,
        onLogout = { viewModel.logout(onLogout) },
        onRefresh = { viewModel.refreshProfile() },
        onImageSelected = { uri -> viewModel.uploadProfileImage(uri) }
    )
}