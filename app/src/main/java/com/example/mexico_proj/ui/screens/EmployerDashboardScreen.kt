package com.example.mexico_proj.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.outlined.WorkOff
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.mexico_proj.AppState
import com.example.mexico_proj.EmployerState
import com.example.mexico_proj.PostedJob

@Composable
fun EmployerDashboardScreen(navController: NavController) {
    val postedJobs = EmployerState.postedJobsState

    Scaffold(
        bottomBar = {
            BottomAppBar(
                actions = {
                    TextButton(onClick = {
                        AppState.exitEmployerMode()
                        navController.navigate("speech_home") {
                            popUpTo(navController.graph.id) { inclusive = true }
                        }
                    }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Regresar a Buscador de Empleo")
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Regresar a Buscador de Empleo")
                    }
                },
                floatingActionButton = {
                    SmallFloatingActionButton(
                        onClick = { navController.navigate("add_job") },
                        elevation = FloatingActionButtonDefaults.elevation(defaultElevation = 2.dp)
                    ) {
                        Icon(Icons.Default.Add, contentDescription = "Añadir Empleo")
                    }
                }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .padding(16.dp)
        ) {
            Text("Tus Trabajos Publicados", style = MaterialTheme.typography.headlineMedium, color = MaterialTheme.colorScheme.primary)
            Spacer(modifier = Modifier.height(16.dp))

            if (postedJobs.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(Icons.Outlined.WorkOff, contentDescription = null, modifier = Modifier.size(80.dp), tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f))
                        Spacer(modifier = Modifier.height(16.dp))
                        Text("No has publicado ningún trabajo todavía.", style = MaterialTheme.typography.bodyLarge, textAlign = TextAlign.Center)
                    }
                }
            } else {
                LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    items(postedJobs) { job ->
                        JobCard(job = job)
                    }
                }
            }
        }
    }
}

@Composable
private fun JobCard(job: PostedJob) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(job.title, style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(8.dp))
            Text(job.description, style = MaterialTheme.typography.bodyMedium, maxLines = 2)
            Spacer(modifier = Modifier.height(16.dp))
            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
                Icon(Icons.Default.People, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                Spacer(modifier = Modifier.width(8.dp))
                Text("${job.applicants.size} Solicitantes", style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}
