package com.example.mexico_proj.ui.screens

import android.Manifest
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.mexico_proj.*
import com.example.mexico_proj.ui.theme.*

@Composable
fun SpeechJobListScreen(navController: NavController) {
    val context = LocalContext.current
    val speechRecognizerManager = remember { SpeechRecognizerManager(context) }
    val ttsManager = remember { TextToSpeechManager(context) }
    val recognizedText by speechRecognizerManager.recognizedText.collectAsState()
    val isListening by speechRecognizerManager.isListening.collectAsState()

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
            if (isGranted) {
                // Pre-emptive stop for TTS before new sequence
                ttsManager.stop()
                
                ttsManager.speak("Di el número del trabajo, por ejemplo: uno, dos, o tres.") {
                    // Start listening only AFTER the prompt finishes speaking
                    speechRecognizerManager.startListening()
                }
            }
        }
    )

    // Handle voice commands
    LaunchedEffect(recognizedText) {
        if (recognizedText.isNotBlank()) {
            val text = recognizedText.lowercase()
            var jobIndex = -1
            
            when {
                text.contains("uno") || text.contains("1") -> jobIndex = 0
                text.contains("dos") || text.contains("2") -> jobIndex = 1
                text.contains("tres") || text.contains("3") -> jobIndex = 2
                text.contains("cuatro") || text.contains("4") -> jobIndex = 3
                text.contains("cinco") || text.contains("5") -> jobIndex = 4
                text.contains("seis") || text.contains("6") -> jobIndex = 5
            }

            if (jobIndex != -1 && jobIndex < MockData.jobs.size) {
                val job = MockData.jobs[jobIndex]
                ttsManager.speak("Abriendo trabajo ${jobIndex + 1}: ${job.title}")
                navController.navigate("job_detail/${job.id}")
            } else {
                ttsManager.speak("No entendí el número. Por favor intenta de nuevo.")
            }
        }
    }
    
    DisposableEffect(Unit) {
        onDispose {
            ttsManager.shutdown()
            speechRecognizerManager.destroy()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background) // Consistent background
            .padding(16.dp) // Consistent padding
    ) {
        // Header with audio prompt
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer // Use theme colors
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
                    tint = MaterialTheme.colorScheme.onPrimaryContainer
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = "Hay ${MockData.jobs.size} empleos disponibles. Toca el ícono de audio para escuchar los detalles, o di el número del trabajo.",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
        }

        // Job list
        LazyColumn(
            modifier = Modifier.weight(1f),
            contentPadding = PaddingValues(vertical = 8.dp)
        ) {
            items(MockData.jobs) { job ->
                SpeechJobCard(
                    job = job,
                    jobNumber = MockData.jobs.indexOf(job) + 1,
                    onAudioClick = {
                        ttsManager.speak(job.description)
                    },
                    onCardClick = {
                        navController.navigate("job_detail/${job.id}")
                    }
                )
                Spacer(modifier = Modifier.height(12.dp))
            }
        }

        // Bottom microphone button for voice selection
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant
            )
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    if (isListening) "Escuchando... Di el número" else "Di el número del trabajo para ver más detalles",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(bottom = 12.dp)
                )
                
                Box(
                    modifier = Modifier
                        .size(70.dp)
                        .clip(CircleShape)
                        .background(if (isListening) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary)
                        .clickable { 
                            permissionLauncher.launch(Manifest.permission.RECORD_AUDIO)
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        Icons.Default.Mic,
                        contentDescription = "Voice",
                        modifier = Modifier.size(40.dp),
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }
        }
    }
}

@Composable
fun SpeechJobCard(
    job: Job,
    jobNumber: Int,
    onAudioClick: () -> Unit,
    onCardClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onCardClick),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Job number badge
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .background(
                        MaterialTheme.colorScheme.primary,
                        CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "$jobNumber",
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.onPrimary,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            // Job info (minimal text)
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = job.title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = job.payRate,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.SemiBold
                )
            }

            // Audio icon button
            IconButton(
                onClick = onAudioClick,
                modifier = Modifier
                    .size(56.dp)
                    .background(
                        MaterialTheme.colorScheme.secondaryContainer,
                        CircleShape
                    )
            ) {
                Icon(
                    Icons.Default.VolumeUp,
                    contentDescription = "Play audio description",
                    modifier = Modifier.size(32.dp),
                    tint = MaterialTheme.colorScheme.onSecondaryContainer
                )
            }
        }
    }
}
