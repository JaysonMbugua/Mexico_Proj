package com.example.mexico_proj

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.navigation.compose.*
import com.example.mexico_proj.ui.components.AppBottomBar
import com.example.mexico_proj.ui.screens.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val navController = rememberNavController()

            Scaffold(
                bottomBar = {
                    AppBottomBar(navController)
                }
            ) { paddingValues ->
                NavHost(
                    navController = navController,
                    startDestination = "home"
                ) {
                    composable("home") { HomeScreen() }
                    composable("settings") { SettingsScreen(navController) }
                    composable("language") { LanguageSelectionScreen(navController) }
                }
            }
        }
    }
}
