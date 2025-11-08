package com.CodeTrainer.codetrainer.ui.features.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.CodeTrainer.codetrainer.domain.repository.AuthResult
import com.CodeTrainer.codetrainer.domain.usecase.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

// 1. Estado de la UI: Guarda todo lo que la pantalla necesita saber
data class LoginUiState(
    val email: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val loginError: String? = null,
    val loginSuccess: Boolean = false
)

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase // Hilt inyecta el Caso de Uso
) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState = _uiState.asStateFlow()

    // 2. Función llamada por la UI cuando el texto del email cambia
    fun onEmailChange(email: String) {
        _uiState.update { it.copy(email = email, loginError = null) }
    }

    // 3. Función llamada por la UI cuando el texto de la contraseña cambia
    fun onPasswordChange(password: String) {
        _uiState.update { it.copy(password = password, loginError = null) }
    }

    // 4. Función llamada por la UI cuando se presiona el botón de "Login"
    fun onLoginClick() {
        viewModelScope.launch {
            // Poner estado de "Cargando"
            _uiState.update { it.copy(isLoading = true, loginError = null) }

            // 5. Llamar al Caso de Uso con el estado actual
            val result = loginUseCase(
                email = _uiState.value.email,
                password = _uiState.value.password
            )

            // 6. Manejar la respuesta de Firebase
            when (result) {
                is AuthResult.Success -> {
                    _uiState.update {
                        it.copy(isLoading = false, loginSuccess = true)
                    }
                }

                is AuthResult.Error -> {
                    // Mapeamos un error de Firebase a un mensaje amigable
                    val friendlyError = when (result.exception.message) {
                        "ERROR_INVALID_EMAIL" -> "El formato del correo no es válido."
                        "ERROR_WRONG_PASSWORD" -> "Contraseña incorrecta."
                        "ERROR_USER_NOT_FOUND" -> "No se encontró un usuario con este correo."
                        else -> "Error desconocido. Intenta de nuevo."
                    }
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            loginError = friendlyError
                        )
                    }
                }
            }
        }
    }
}