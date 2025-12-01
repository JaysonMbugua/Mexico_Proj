package com.example.mexico_proj.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.example.mexico_proj.AppMode

// Color schemes
private val BlueTheme = lightColorScheme(
    primary = Blue600,
    secondary = Blue500,
    tertiary = Blue700,
    background = Blue50,
    surface = Blue100,
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = TextPrimary,
    onSurface = TextPrimary
)

private val GreenTheme = lightColorScheme(
    primary = Green600,
    secondary = Green500,
    tertiary = Green700,
    background = Green50,
    surface = Green100,
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = TextPrimary,
    onSurface = TextPrimary
)

@Composable
fun MexicoProjTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    appMode: AppMode = AppMode.SPEECH_BASED,
    content: @Composable () -> Unit
) {
    val colorScheme = when (appMode) {
        AppMode.SPEECH_BASED -> BlueTheme
        AppMode.IMAGE_BASED -> GreenTheme
        AppMode.EMPLOYER -> BlueTheme // Default to Blue for Employer mode
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
