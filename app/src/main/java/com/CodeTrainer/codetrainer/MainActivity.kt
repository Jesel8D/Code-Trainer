package com.CodeTrainer.codetrainer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.CodeTrainer.codetrainer.ui.navigation.AppNavigation
import com.CodeTrainer.codetrainer.ui.theme.CodeTrainerTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContent {
            // 1. Aplicamos nuestro tema (de ui/theme/Theme.kt)
            CodeTrainerTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // 2. ¡Llamamos a nuestro sistema de navegación!
                    AppNavigation()
                }
            }
        }
    }
}