package com.example.mexico_proj

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

object AppState {
    var currentMode by mutableStateOf(AppMode.SPEECH_BASED)
        private set

    private var lastJobSeekerMode: AppMode = AppMode.SPEECH_BASED

    fun toggleMode() {
        currentMode = when (currentMode) {
            AppMode.SPEECH_BASED -> AppMode.IMAGE_BASED
            AppMode.IMAGE_BASED -> AppMode.SPEECH_BASED
            AppMode.EMPLOYER -> lastJobSeekerMode // Should not happen, but as a fallback
        }
        lastJobSeekerMode = currentMode
    }

    fun enterEmployerMode() {
        // Save the current mode so we can return to it
        if (currentMode != AppMode.EMPLOYER) {
            lastJobSeekerMode = currentMode
        }
        currentMode = AppMode.EMPLOYER
    }

    fun exitEmployerMode() {
        currentMode = lastJobSeekerMode
    }
}
