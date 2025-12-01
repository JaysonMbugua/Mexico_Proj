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
                "speech_home", "image_home", "speech_jobs", "image_jobs", "receipt", "settings"
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
                                    AppState.toggleMode()
                                    val destination = when (AppState.currentMode) {
                                        AppMode.SPEECH_BASED -> "speech_home"
                                        AppMode.IMAGE_BASED -> "image_home"
                                        else -> "speech_home"
                                    }
                                    navController.navigate(destination) {
                                        popUpTo(navController.graph.startDestinationId) { inclusive = true }
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
                        composable("receipt") { ReceiptScreen(navController) }
                        composable("settings") { SettingsScreen(navController) }
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
