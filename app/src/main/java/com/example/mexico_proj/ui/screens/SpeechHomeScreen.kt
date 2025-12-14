package com.example.mexico_proj.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material.icons.filled.Work
import androidx.compose.material.icons.filled.AccountBalanceWallet
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.mexico_proj.AppState
import com.example.mexico_proj.MockData
import com.example.mexico_proj.UsabilityLogger
import kotlinx.coroutines.delay

@Composable
fun SpeechHomeScreen(navController: NavController) {
    var isListening by remember { mutableStateOf(false) }
    var currentPrompt by remember { mutableStateOf("Bienvenido, ${MockData.currentUser.name}. ¿Qué quieres hacer hoy? Di 'Buscar Empleo', 'Ver Mi Pago', o 'Ajustes'.") }
    var showVoiceOptions by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        UsabilityLogger.logNavigation("", "SpeechHome", AppState.currentMode)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Welcome message
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 32.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer
            )
        ) {
            Row(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    Icons.Default.VolumeUp,
                    contentDescription = "Audio",
                    modifier = Modifier.size(32.dp),
                    tint = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = currentPrompt,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
        }

        Spacer(modifier = Modifier.height(48.dp))

        // Large microphone button
        Box(
            modifier = Modifier
                .size(150.dp)
                .clip(CircleShape)
                .background(
                    if (isListening)
                        MaterialTheme.colorScheme.secondary
                    else
                        MaterialTheme.colorScheme.primary
                )
                .clickable {
                    isListening = !isListening
                    if (isListening) {
                        showVoiceOptions = true
                        UsabilityLogger.logInteraction("MICROPHONE_TAP", AppState.currentMode, "User tapped microphone")
                    }
                },
            contentAlignment = Alignment.Center
        ) {
            Icon(
                Icons.Default.Mic,
                contentDescription = "Microphone",
                modifier = Modifier.size(80.dp),
                tint = MaterialTheme.colorScheme.onPrimary
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = if (isListening) "Escuchando..." else "Toca para hablar",
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.primary,
            textAlign = TextAlign.Center
        )

        // Voice command simulation options
        if (showVoiceOptions) {
            Spacer(modifier = Modifier.height(32.dp))
            
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        "Simular comando de voz:",
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(bottom = 12.dp)
                    )
                    
                    VoiceCommandButton(
                        text = "Buscar Empleo",
                        icon = Icons.Default.Work,
                        onClick = {
                            UsabilityLogger.logInteraction("VOICE_COMMAND", AppState.currentMode, "Buscar Empleo")
                            navController.navigate("speech_jobs")
                            isListening = false
                            showVoiceOptions = false
                        }
                    )
                    
                    VoiceCommandButton(
                        text = "Ver Mi Pago",
                        icon = Icons.Default.AccountBalanceWallet,
                        onClick = {
                            UsabilityLogger.logInteraction("VOICE_COMMAND", AppState.currentMode, "Ver Mi Pago")
                            navController.navigate("receipt")
                            isListening = false
                            showVoiceOptions = false
                        }
                    )
                    
                    VoiceCommandButton(
                        text = "Ajustes",
                        icon = Icons.Default.Settings,
                        onClick = {
                            UsabilityLogger.logInteraction("VOICE_COMMAND", AppState.currentMode, "Ajustes")
                            navController.navigate("settings")
                            isListening = false
                            showVoiceOptions = false
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun VoiceCommandButton(
    text: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    onClick: () -> Unit
) {
    OutlinedButton(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Icon(icon, contentDescription = null, modifier = Modifier.size(24.dp))
        Spacer(modifier = Modifier.width(12.dp))
        Text(text, modifier = Modifier.weight(1f))
    }
}

