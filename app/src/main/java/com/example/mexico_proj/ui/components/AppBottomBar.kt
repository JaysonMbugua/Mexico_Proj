package com.example.mexico_proj.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Work
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.mexico_proj.AppMode
import com.example.mexico_proj.AppState

@Composable
fun AppBottomBar(navController: NavHostController) {
    val currentMode = AppState.currentMode
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    
    val tabs = when (currentMode) {
        AppMode.SPEECH_BASED -> listOf(
            BottomTab("speech_home", "Inicio", Icons.Default.Home),
            BottomTab("speech_jobs", "Empleos", Icons.Default.Work),
            BottomTab("receipt", "Pago", Icons.Default.Description),
            BottomTab("settings", "Ajustes", Icons.Default.Settings)
        )
        AppMode.IMAGE_BASED -> listOf(
            BottomTab("image_home", "Inicio", Icons.Default.Home),
            BottomTab("image_jobs", "Empleos", Icons.Default.Work),
            BottomTab("receipt", "Pago", Icons.Default.Description),
            BottomTab("settings", "Datos", Icons.Default.Settings)
        )
        AppMode.EMPLOYER -> emptyList()
    }

    NavigationBar(
        containerColor = MaterialTheme.colorScheme.surface,
        contentColor = MaterialTheme.colorScheme.primary
    ) {
        val currentRoute = navBackStackEntry?.destination?.route

        tabs.forEach { tab ->
            NavigationBarItem(
                icon = { Icon(tab.icon, contentDescription = tab.label) },
                label = { Text(tab.label) },
                selected = currentRoute?.startsWith(tab.route) == true,
                onClick = {
                    navController.navigate(tab.route) {
                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.primary,
                    selectedTextColor = MaterialTheme.colorScheme.primary,
                    indicatorColor = MaterialTheme.colorScheme.primaryContainer,
                    unselectedIconColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                    unselectedTextColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                )
            )
        }
    }
}
