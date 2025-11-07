package com.CodeTrainer.codetrainer.ui.features.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.CodeTrainer.codetrainer.domain.usecase.GetCurrentUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

// 1. Definimos los posibles estados de "carga"
sealed interface AuthStatus {
    data object Loading : AuthStatus
    data object Authenticated : AuthStatus
    data object Unauthenticated : AuthStatus
}

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val getCurrentUserUseCase: GetCurrentUserUseCase
) : ViewModel() {

    // 2. Un StateFlow "privado" que el ViewModel controla
    private val _authStatus = MutableStateFlow<AuthStatus>(AuthStatus.Loading)

    // 3. Un StateFlow "público" que la UI solo puede leer
    val authStatus = _authStatus.asStateFlow()

    init {
        // 4. En el momento en que el ViewModel se crea...
        checkAuthStatus()
    }

    private fun checkAuthStatus() {
        viewModelScope.launch {
            // (Opcional) Añadimos un pequeño delay para que el logo se vea
            delay(1000L) // 1 segundo

            // 5. Llamamos a nuestro Caso de Uso
            val user = getCurrentUserUseCase()

            // 6. Actualizamos el estado
            if (user != null) {
                _authStatus.value = AuthStatus.Authenticated
            } else {
                _authStatus.value = AuthStatus.Unauthenticated
            }
        }
    }
}