package com.example.mexico_proj.ui.screens

import android.Manifest
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.mexico_proj.* 

@Composable
fun SpeechHomeScreen(navController: NavController) {
    val context = LocalContext.current
    val speechRecognizerManager = remember { SpeechRecognizerManager(context) }
    val ttsManager = remember { TextToSpeechManager(context) }
    val isListening by speechRecognizerManager.isListening.collectAsState()
    val isSpeaking by ttsManager.isSpeaking.collectAsState()
    val recognizedText by speechRecognizerManager.recognizedText.collectAsState()
    
    var hasSpokenWelcome by remember { mutableStateOf(false) }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
            if (isGranted) {
                speechRecognizerManager.startListening()
            } 
        }
    )

    LaunchedEffect(Unit) {
        if (!hasSpokenWelcome) {
            hasSpokenWelcome = true
            ttsManager.speak("Bienvenido. ¿Qué quieres hacer hoy? Puedes decir: buscar empleo, ver mi pago, o ajustes.")
        }
    }

    LaunchedEffect(recognizedText) {
        if (recognizedText.isNotBlank()) {
            val jobsText = MockData.jobs.joinToString(separator = ", ") { it.title }
            when {
                recognizedText.contains("buscar empleo", ignoreCase = true) || 
                recognizedText.contains("buscar trabajo", ignoreCase = true) ||
                recognizedText.contains("empleo", ignoreCase = true) ||
                recognizedText.contains("trabajo", ignoreCase = true) -> {
                    ttsManager.speak("Aquí están los trabajos disponibles: $jobsText. ¿Te interesa alguno?")
                }
                recognizedText.contains("ver mi pago", ignoreCase = true) ||
                recognizedText.contains("mi pago", ignoreCase = true) ||
                recognizedText.contains("pago", ignoreCase = true) -> {
                    ttsManager.speak("Tu último pago fue de ${MockData.lastPaymentReceipt.netPay} pesos. El pago fue el ${MockData.lastPaymentReceipt.date}.")
                }
                recognizedText.contains("ajustes", ignoreCase = true) ||
                recognizedText.contains("configuración", ignoreCase = true) -> {
                    ttsManager.speak("No hay ajustes para configurar.")
                }
                recognizedText.contains("ayuda", ignoreCase = true) ||
                recognizedText.contains("opciones", ignoreCase = true) -> {
                    ttsManager.speak("Puedes decir: buscar empleo para ver trabajos disponibles, ver mi pago para consultar tu último pago, o ajustes para configuración.")
                }
                recognizedText.contains("repetir", ignoreCase = true) -> {
                    ttsManager.speak("Puedes decir: buscar empleo, ver mi pago, o ajustes.")
                }
                else -> ttsManager.speak("No entendí. Por favor, intenta de nuevo. Puedes decir: buscar empleo, ver mi pago, ayuda, o ajustes.")
            }
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            ttsManager.shutdown()
        }
    }

    val infiniteTransition = rememberInfiniteTransition(label = "speaking")
    val speakingScale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.15f,
        animationSpec = infiniteRepeatable(
            animation = tween(600, easing = EaseInOutSine),
            repeatMode = RepeatMode.Reverse
        ),
        label = "speakingScale"
    )
    
    val listeningScale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.1f,
        animationSpec = infiniteRepeatable(
            animation = tween(400, easing = EaseInOutSine),
            repeatMode = RepeatMode.Reverse
        ),
        label = "listeningScale"
    )

    val buttonColor by animateColorAsState(
        targetValue = when {
            isSpeaking -> MaterialTheme.colorScheme.tertiary
            isListening -> MaterialTheme.colorScheme.secondary
            else -> MaterialTheme.colorScheme.primary
        },
        animationSpec = tween(300),
        label = "buttonColor"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Card(
            modifier = Modifier.fillMaxWidth().padding(bottom = 32.dp),
            colors = CardDefaults.cardColors(
                containerColor = if (isSpeaking) 
                    MaterialTheme.colorScheme.tertiaryContainer 
                else 
                    MaterialTheme.colorScheme.primaryContainer
            )
        ) {
            Row(
                modifier = Modifier.padding(16.dp).fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = if (isSpeaking) Icons.Default.VolumeUp else Icons.Default.VolumeOff, 
                    contentDescription = "Audio", 
                    modifier = Modifier
                        .size(32.dp)
                        .scale(if (isSpeaking) speakingScale else 1f),
                    tint = if (isSpeaking) 
                        MaterialTheme.colorScheme.tertiary 
                    else 
                        MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = if (isSpeaking) 
                        "Hablando..." 
                    else 
                        "Bienvenido. ¿Qué quieres hacer hoy?",
                    style = MaterialTheme.typography.bodyLarge, 
                    color = if (isSpeaking)
                        MaterialTheme.colorScheme.onTertiaryContainer
                    else
                        MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
        }

        Spacer(modifier = Modifier.height(48.dp))

        Box(
            modifier = Modifier
                .size(150.dp)
                .scale(
                    when {
                        isSpeaking -> speakingScale
                        isListening -> listeningScale
                        else -> 1f
                    }
                )
                .clip(CircleShape)
                .background(buttonColor)
                .clickable(enabled = !isSpeaking) {
                    if (!isSpeaking) {
                        permissionLauncher.launch(Manifest.permission.RECORD_AUDIO)
                    }
                },
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = when {
                    isSpeaking -> Icons.Default.VolumeUp
                    isListening -> Icons.Default.Mic
                    else -> Icons.Default.Mic
                }, 
                contentDescription = "Microphone", 
                modifier = Modifier.size(80.dp), 
                tint = MaterialTheme.colorScheme.onPrimary
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = when {
                isSpeaking -> "Hablando..."
                isListening -> "Escuchando..."
                else -> "Toca para hablar"
            },
            style = MaterialTheme.typography.titleLarge,
            color = buttonColor,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = "Di: \"buscar empleo\", \"ver mi pago\", o \"ayuda\"",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center
        )
    }
}