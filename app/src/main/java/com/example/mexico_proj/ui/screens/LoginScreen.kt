package com.example.mexico_proj.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.mexico_proj.AppState
import com.example.mexico_proj.AppMode

@Composable
fun LoginScreen(navController: NavController) {
    Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Welcome!", style = MaterialTheme.typography.headlineLarge)
            Spacer(modifier = Modifier.height(32.dp))
            Button(onClick = {
                val destination = when (AppState.currentMode) {
                    AppMode.SPEECH_BASED -> "speech_home"
                    AppMode.IMAGE_BASED -> "image_home"
                    else -> "speech_home" // Default
                }
                navController.navigate(destination)
            }) {
                Text("Job Seeker")
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = {
                navController.navigate("employer_login")
            }) {
                Text("Employer")
            }
        }
    }
}
