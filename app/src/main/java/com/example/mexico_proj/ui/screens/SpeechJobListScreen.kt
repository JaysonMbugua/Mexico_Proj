package com.example.mexico_proj.ui.screens

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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.mexico_proj.*
import com.example.mexico_proj.ui.theme.*

@Composable
fun SpeechJobListScreen(navController: NavController) {
    var showVoiceInput by remember { mutableStateOf(false) }
    var selectedJobForAudio by remember { mutableStateOf<Job?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Blue50)
    ) {
        // Header with audio prompt
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = Blue100
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
                    tint = Blue600
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = "Hay ${MockData.jobs.size} empleos disponibles. Toca el ícono de audio para escuchar los detalles, o di el número del trabajo.",
                    style = MaterialTheme.typography.bodyLarge,
                    color = Blue700
                )
            }
        }

        // Job list
        LazyColumn(
            modifier = Modifier.weight(1f),
            contentPadding = PaddingValues(horizontal = 16.dp)
        ) {
            items(MockData.jobs) { job ->
                SpeechJobCard(
                    job = job,
                    jobNumber = MockData.jobs.indexOf(job) + 1,
                    onAudioClick = {
                        selectedJobForAudio = job
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
                .padding(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = Blue100
            )
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    "Di el número del trabajo para ver más detalles",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(bottom = 12.dp)
                )
                
                Box(
                    modifier = Modifier
                        .size(70.dp)
                        .clip(CircleShape)
                        .background(Blue600)
                        .clickable { showVoiceInput = !showVoiceInput },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        Icons.Default.Mic,
                        contentDescription = "Voice",
                        modifier = Modifier.size(40.dp),
                        tint = Color.White
                    )
                }
            }
        }
    }

    // Voice number selection dialog
    if (showVoiceInput) {
        AlertDialog(
            onDismissRequest = { showVoiceInput = false },
            title = { Text("Seleccionar trabajo") },
            text = {
                Column {
                    Text("Simula decir el número del trabajo:")
                    Spacer(modifier = Modifier.height(16.dp))
                    MockData.jobs.forEachIndexed { index, job ->
                        Button(
                            onClick = {
                                navController.navigate("job_detail/${job.id}")
                                showVoiceInput = false
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp)
                        ) {
                            Text("${index + 1}. ${job.title}")
                        }
                    }
                }
            },
            confirmButton = {},
            dismissButton = {
                TextButton(onClick = { showVoiceInput = false }) {
                    Text("Cancelar")
                }
            }
        )
    }

    // Job audio playback dialog
    if (selectedJobForAudio != null) {
        AlertDialog(
            onDismissRequest = { selectedJobForAudio = null },
            icon = {
                Icon(Icons.Default.VolumeUp, contentDescription = null, modifier = Modifier.size(48.dp))
            },
            title = { Text("Reproduciendo descripción") },
            text = {
                Column {
                    Text(
                        "🔊 Audio (simulado):",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("\"${selectedJobForAudio?.description}\"")
                    Spacer(modifier = Modifier.height(12.dp))
                    LinearProgressIndicator(
                        modifier = Modifier.fillMaxWidth(),
                        color = Blue600
                    )
                }
            },
            confirmButton = {
                TextButton(onClick = { selectedJobForAudio = null }) {
                    Text("Cerrar")
                }
            }
        )
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
            containerColor = Blue100
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
                        Blue600,
                        CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "$jobNumber",
                    style = MaterialTheme.typography.headlineSmall,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            // Job info (minimal text)
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = job.title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = job.payRate,
                    style = MaterialTheme.typography.bodyLarge,
                    color = Blue600,
                    fontWeight = FontWeight.SemiBold
                )
            }

            // Audio icon button
            IconButton(
                onClick = onAudioClick,
                modifier = Modifier
                    .size(56.dp)
                    .background(
                        Blue100,
                        CircleShape
                    )
            ) {
                Icon(
                    Icons.Default.VolumeUp,
                    contentDescription = "Play audio description",
                    modifier = Modifier.size(32.dp),
                    tint = Blue600
                )
            }
        }
    }
}
