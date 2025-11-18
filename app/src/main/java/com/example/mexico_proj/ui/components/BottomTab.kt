package com.example.mexico_proj.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomTab(
    val route: String,
    val icon: ImageVector,
    val label: String
) {
    object Home : BottomTab("home", Icons.Filled.Home, "Home")
    object Settings : BottomTab("settings", Icons.Filled.Settings, "Settings")

    companion object {
        val tabs = listOf(Home, Settings)
    }
}

