package com.CodeTrainer.codetrainer.ui.features.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.CodeTrainer.codetrainer.domain.repository.AuthResult
import com.CodeTrainer.codetrainer.domain.usecase.RegisterUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

// 1. El estado que describe la UI de Registro
data class RegisterUiState(
    val name: String = "",
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val isLoading: Boolean = false,
    val registerError: String? = null,
    val registerSuccess: Boolean = false
)

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerUseCase: RegisterUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(RegisterUiState())
    val uiState = _uiState.asStateFlow()

    // --- Eventos de la UI ---
    fun onNameChange(name: String) {
        _uiState.update { it.copy(name = name, registerError = null) }
    }

    fun onEmailChange(email: String) {
        _uiState.update { it.copy(email = email, registerError = null) }
    }

    fun onPasswordChange(password: String) {
        _uiState.update { it.copy(password = password, registerError = null) }
    }

    fun onConfirmPasswordChange(confirmPassword: String) {
        _uiState.update { it.copy(confirmPassword = confirmPassword, registerError = null) }
    }

    // --- Lógica de Registro ---
    fun onRegisterClick() {
        // 1. Validación del lado del cliente (¡muy importante!)
        if (!isValidInput()) return

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, registerError = null) }

            val result = registerUseCase(
                email = _uiState.value.email,
                password = _uiState.value.password
            )

            when (result) {
                is AuthResult.Success -> {
                    _uiState.update {
                        it.copy(isLoading = false, registerSuccess = true)
                    }
                }

                is AuthResult.Error -> {
                    // Mapeamos errores de Firebase a mensajes amigables
                    val friendlyError = when {
                        result.exception.message?.contains("EMAIL_EXISTS") == true -> "Este correo ya está en uso."
                        result.exception.message?.contains("WEAK_PASSWORD") == true -> "La contraseña debe tener al menos 6 caracteres."
                        else -> "Error desconocido. Intenta de nuevo."
                    }
                    _uiState.update {
                        it.copy(isLoading = false, registerError = friendlyError)
                    }
                }
            }
        }
    }

    private fun isValidInput(): Boolean {
        val state = _uiState.value
        if (state.name.isBlank() || state.email.isBlank() || state.password.isBlank()) {
            _uiState.update { it.copy(registerError = "Todos los campos son obligatorios.") }
            return false
        }
        if (state.password != state.confirmPassword) {
            _uiState.update { it.copy(registerError = "Las contraseñas no coinciden.") }
            return false
        }
        // (Podríamos añadir validación de email aquí, pero Firebase ya lo hace)
        return true
    }
}