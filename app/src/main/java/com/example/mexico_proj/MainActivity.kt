package com.example.mexico_proj

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PhotoLibrary
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument
import com.example.mexico_proj.ui.components.AppBottomBar
import com.example.mexico_proj.ui.screens.*
import com.example.mexico_proj.ui.theme.MexicoProjTheme
import com.example.mexico_proj.AppState

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val currentMode by remember { derivedStateOf { AppState.currentMode } }
            val navController = rememberNavController()

            // Determine if the FAB and Bottom Bar should be shown
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry?.destination?.route
            val shouldShowMainUI = currentRoute in listOf(
                "speech_home", "image_home", 
                "speech_jobs", "image_jobs", 
                "speech_receipt", "image_receipt", 
                "speech_settings", "image_settings"
            )

            MexicoProjTheme(appMode = currentMode) {
                Scaffold(
                    bottomBar = {
                        if (shouldShowMainUI) {
                            AppBottomBar(navController)
                        }
                    },
                    floatingActionButton = {
                        if (shouldShowMainUI) {
                            FloatingActionButton(
                                onClick = {
                                    val newMode = if (AppState.currentMode == AppMode.SPEECH_BASED) 
                                        AppMode.IMAGE_BASED 
                                    else 
                                        AppMode.SPEECH_BASED
                                    
                                    AppState.toggleMode()

                                    // Smart navigation to preserve state
                                    val targetRoute = when (currentRoute) {
                                        "speech_home" -> if (newMode == AppMode.IMAGE_BASED) "image_home" else null
                                        "image_home" -> if (newMode == AppMode.SPEECH_BASED) "speech_home" else null
                                        "speech_jobs" -> if (newMode == AppMode.IMAGE_BASED) "image_jobs" else null
                                        "image_jobs" -> if (newMode == AppMode.SPEECH_BASED) "speech_jobs" else null
                                        "speech_receipt" -> if (newMode == AppMode.IMAGE_BASED) "image_receipt" else null
                                        "image_receipt" -> if (newMode == AppMode.SPEECH_BASED) "speech_receipt" else null
                                        "speech_settings" -> if (newMode == AppMode.IMAGE_BASED) "image_settings" else null
                                        "image_settings" -> if (newMode == AppMode.SPEECH_BASED) "speech_settings" else null
                                        else -> null
                                    }

                                    if (targetRoute != null) {
                                        navController.navigate(targetRoute) {
                                            // Pop up to the start destination of the graph to avoid building up a large stack
                                            // but preserve the state of the new destination if possible or just swap
                                            popUpTo(navController.graph.startDestinationId) { 
                                                saveState = true 
                                            }
                                            launchSingleTop = true
                                            restoreState = true
                                        }
                                    }
                                },
                                containerColor = MaterialTheme.colorScheme.secondary,
                                shape = CircleShape
                            ) {
                                Icon(
                                    imageVector = if (currentMode == AppMode.SPEECH_BASED) Icons.Default.PhotoLibrary else Icons.Default.Mic,
                                    contentDescription = "Switch Mode",
                                    tint = Color.White,
                                    modifier = Modifier.size(28.dp)
                                )
                            }
                        }
                    }
                ) { paddingValues ->
                    NavHost(
                        navController = navController,
                        startDestination = "speech_home",
                        modifier = Modifier.padding(paddingValues)
                    ) {
                        composable("speech_home") { SpeechHomeScreen(navController) }
                        composable("image_home") { ImageHomeScreen(navController) }
                        composable("speech_jobs") { SpeechJobListScreen(navController) }
                        composable("image_jobs") { ImageJobListScreen(navController) }
                        
                        // Split routes for receipt to enable smooth transitions
                        composable("speech_receipt") { ReceiptScreen(navController) }
                        composable("image_receipt") { ReceiptScreen(navController) }
                        
                        // Split routes for settings to enable smooth transitions
                        composable("speech_settings") { SettingsScreen(navController) }
                        composable("image_settings") { SettingsScreen(navController) }
                        
                        composable("employer_login") { EmployerLoginScreen(navController) }
                        composable("employer_dashboard") { EmployerDashboardScreen(navController) }
                        composable("add_job") { AddJobScreen(navController) }
                        
                        composable(
                            route = "job_detail/{jobId}",
                            arguments = listOf(navArgument("jobId") { type = NavType.IntType })
                        ) { backStackEntry ->
                            val jobId = backStackEntry.arguments?.getInt("jobId") ?: 1
                            JobDetailScreen(navController, jobId)
                        }

                        composable("language") { LanguageSelectionScreen(navController) }
                    }
                }
            }
        }
    }
}
