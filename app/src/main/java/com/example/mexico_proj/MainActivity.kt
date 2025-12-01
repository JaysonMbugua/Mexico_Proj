package com.example.mexico_proj

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
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

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val currentMode by remember { derivedStateOf { AppState.currentMode } }
            
            MexicoProjTheme(appMode = currentMode) {
                val navController = rememberNavController()

                Scaffold(
                    bottomBar = {
                        AppBottomBar(navController)
                    },
                    floatingActionButton = {
                        // Mode switching FAB
                        FloatingActionButton(
                            onClick = {
                                AppState.toggleMode()
                                // Navigate to the appropriate home screen
                                val destination = when (AppState.currentMode) {
                                    AppMode.SPEECH_BASED -> "speech_home"
                                    AppMode.IMAGE_BASED -> "image_home"
                                }
                                navController.navigate(destination) {
                                    popUpTo(navController.graph.startDestinationId) {
                                        inclusive = true
                                    }
                                }
                            },
                            containerColor = MaterialTheme.colorScheme.secondary,
                            shape = CircleShape
                        ) {
                            Icon(
                                imageVector = if (currentMode == AppMode.SPEECH_BASED) 
                                    Icons.Default.PhotoLibrary 
                                else 
                                    Icons.Default.Mic,
                                contentDescription = "Cambiar modo",
                                tint = Color.White,
                                modifier = Modifier.size(28.dp)
                            )
                        }
                    }
                ) { paddingValues ->
                    NavHost(
                        navController = navController,
                        startDestination = "speech_home",
                        modifier = Modifier.padding(paddingValues)
                    ) {
                        // Prototype A - Speech-Based screens
                        composable("speech_home") { 
                            SpeechHomeScreen(navController) 
                        }
                        composable("speech_jobs") { 
                            SpeechJobListScreen(navController) 
                        }

                        // Prototype B - Image-Based screens
                        composable("image_home") { 
                            ImageHomeScreen(navController) 
                        }
                        composable("image_jobs") { 
                            ImageJobListScreen(navController) 
                        }

                        // Shared screens
                        composable("receipt") { 
                            ReceiptScreen(navController) 
                        }
                        composable("settings") { 
                            SettingsScreen(navController) 
                        }
                        
                        // Job detail screen with argument
                        composable(
                            route = "job_detail/{jobId}",
                            arguments = listOf(navArgument("jobId") { type = NavType.IntType })
                        ) { backStackEntry ->
                            val jobId = backStackEntry.arguments?.getInt("jobId") ?: 1
                            JobDetailScreen(navController, jobId)
                        }

                        // Keep language selection for future use
                        composable("language") { 
                            LanguageSelectionScreen(navController) 
                        }
                        
                        // Add Job screen for employers
                        composable("add_job") {
                            AddJobScreen(navController)
                        }
                    }
                }
            }
        }
    }
}
