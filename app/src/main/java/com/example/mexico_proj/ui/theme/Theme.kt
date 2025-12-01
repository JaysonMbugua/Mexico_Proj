package com.example.mexico_proj.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import com.example.mexico_proj.AppMode

// Prototype A - Speech-Based (Blue Theme)
private val SpeechBasedColorScheme = lightColorScheme(
    primary = Blue600,
    onPrimary = White,
    primaryContainer = Blue100,
    onPrimaryContainer = Blue700,
    secondary = Blue500,
    onSecondary = White,
    surface = White,
    onSurface = TextPrimary,
    background = Blue50,
    onBackground = TextPrimary
)

// Prototype B - Image-Based (Green Theme)
private val ImageBasedColorScheme = lightColorScheme(
    primary = Green600,
    onPrimary = White,
    primaryContainer = Green100,
    onPrimaryContainer = Green700,
    secondary = Green500,
    onSecondary = White,
    surface = White,
    onSurface = TextPrimary,
    background = Green50,
    onBackground = TextPrimary
)

@Composable
fun MexicoProjTheme(
    appMode: AppMode = AppMode.SPEECH_BASED,
    content: @Composable () -> Unit
) {
    val colorScheme = when (appMode) {
        AppMode.SPEECH_BASED -> SpeechBasedColorScheme
        AppMode.IMAGE_BASED -> ImageBasedColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}