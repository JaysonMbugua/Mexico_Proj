package com.example.mexico_proj

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

/**
 * Global app state manager for prototype mode switching
 */
object AppState {
    var currentMode by mutableStateOf(AppMode.SPEECH_BASED)
        private set
    
    fun toggleMode() {
        currentMode = when (currentMode) {
            AppMode.SPEECH_BASED -> AppMode.IMAGE_BASED
            AppMode.IMAGE_BASED -> AppMode.SPEECH_BASED
        }
        UsabilityLogger.logInteraction(
            "MODE_SWITCH",
            currentMode,
            "Switched to ${currentMode.name}"
        )
    }
    
    fun setMode(mode: AppMode) {
        currentMode = mode
    }
}

