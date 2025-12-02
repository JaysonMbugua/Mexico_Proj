package com.example.mexico_proj.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.example.mexico_proj.AppMode

// Blue theme for Speech-Based prototype
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
    onSurface = TextPrimary,
    primaryContainer = Blue100,
    onPrimaryContainer = Blue700,
    secondaryContainer = Blue50,
    onSecondaryContainer = Blue600,
    tertiaryContainer = Blue50,
    onTertiaryContainer = Blue600
)

// Green theme for Image-Based prototype
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
    onSurface = TextPrimary,
    primaryContainer = Green100,
    onPrimaryContainer = Green700,
    secondaryContainer = Green50,
    onSecondaryContainer = Green600,
    tertiaryContainer = Green50,
    onTertiaryContainer = Green600
)

// Purple theme for Employer prototype
private val EmployerTheme = lightColorScheme(
    primary = Purple600,
    secondary = Purple500,
    tertiary = Purple700,
    background = Purple50,
    surface = Purple100,
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = TextPrimary,
    onSurface = TextPrimary,
    primaryContainer = Purple100,
    onPrimaryContainer = Purple700,
    secondaryContainer = Purple50,
    onSecondaryContainer = Purple600,
    tertiaryContainer = Purple50,
    onTertiaryContainer = Purple600
)

@Composable
fun MexicoProjTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false, // Explicitly disable dynamic color
    appMode: AppMode = AppMode.SPEECH_BASED,
    content: @Composable () -> Unit
) {
    val colorScheme = when (appMode) {
        AppMode.SPEECH_BASED -> BlueTheme
        AppMode.IMAGE_BASED -> GreenTheme
        AppMode.EMPLOYER -> EmployerTheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
