package com.example.mexico_proj.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.filled.Work
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Block
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.mexico_proj.*
import com.example.mexico_proj.data.AppDatabase
import com.example.mexico_proj.data.JobEntity
import com.example.mexico_proj.ui.theme.*

@Composable
fun ImageJobListScreen(navController: NavController) {
    val context = LocalContext.current
    val database = remember { AppDatabase.getDatabase(context) }
    val jobs by database.jobDao().getAllJobs().collectAsState(initial = emptyList())
    
    LaunchedEffect(Unit) {
        UsabilityLogger.startTask("TASK1_FIND_JOB", AppState.currentMode, "Image-based job search")
        UsabilityLogger.logNavigation("ImageHome", "ImageJobList", AppState.currentMode)
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {
            // Header
            Surface(
                modifier = Modifier.fillMaxWidth(),
                color = MaterialTheme.colorScheme.primary
            ) {
                Text(
                    text = "Trabajos Disponibles",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    modifier = Modifier.padding(16.dp)
                )
            }

            // Visual legend for job safety
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            ) {
                Row(
                    modifier = Modifier
                        .padding(12.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    LegendItem(
                        icon = Icons.Default.CheckCircle,
                        color = SafeGreen,
                        label = "Seguro/Con beneficios"
                    )
                    LegendItem(
                        icon = Icons.Default.Warning,
                        color = DangerRed,
                        label = "Sin beneficios"
                    )
                }
            }

            // Job list from database
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
            ) {
                items(jobs) { jobEntity ->
                    ImageJobCardFromDb(
                        job = jobEntity,
                        onAcceptClick = {
                            UsabilityLogger.completeTask("TASK1_FIND_JOB", AppState.currentMode, "Job accepted: ${jobEntity.title}")
                            UsabilityLogger.logInteraction("JOB_ACCEPT", AppState.currentMode, "Job: ${jobEntity.title}")
                            navController.navigate("job_detail/${jobEntity.id}")
                        }
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }
                
                // Add spacing at bottom for FAB
                item {
                    Spacer(modifier = Modifier.height(80.dp))
                }
            }
        }
        
        // Floating Action Button to add new job
        FloatingActionButton(
            onClick = { navController.navigate("add_job") },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp),
            containerColor = SafeGreen,
            shape = CircleShape
        ) {
            Icon(
                Icons.Default.Add,
                contentDescription = "Agregar Trabajo",
                tint = Color.White
            )
        }
    }
}

@Composable
fun LegendItem(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    color: Color,
    label: String
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = color,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall
        )
    }
}

@Composable
fun ImageJobCard(
    job: Job,
    onAcceptClick: () -> Unit
) {
    val backgroundColor = if (job.hasBenefits && job.isSafe) SafeGreenBg else DangerRedBg
    val iconColor = if (job.hasBenefits && job.isSafe) SafeGreen else DangerRed
    val icon = if (job.hasBenefits && job.isSafe) Icons.Default.CheckCircle else Icons.Default.Warning

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = backgroundColor
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {
            // Safety indicator icon - Large and prominent
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = if (job.isSafe) "Trabajo seguro" else "Precaución",
                    tint = iconColor,
                    modifier = Modifier.size(56.dp)
                )

                // Work icon
                Icon(
                    imageVector = Icons.Default.Work,
                    contentDescription = "Trabajo",
                    tint = iconColor,
                    modifier = Modifier.size(48.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Job title - Large text
            Text(
                text = job.title,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = TextPrimary
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Pay rate - Prominent display
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.AttachMoney,
                    contentDescription = "Pago",
                    tint = iconColor,
                    modifier = Modifier.size(32.dp)
                )
                Text(
                    text = job.payRate,
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = iconColor
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Location
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.LocationOn,
                    contentDescription = "Ubicación",
                    tint = TextSecondary,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = job.location,
                    style = MaterialTheme.typography.bodyLarge,
                    color = TextSecondary
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Key job details with icons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                JobDetailIcon(
                    icon = Icons.Default.Schedule,
                    text = job.hoursPerWeek
                )
                JobDetailIcon(
                    icon = if (job.hasBenefits) Icons.Default.Lock else Icons.Default.Block,
                    text = if (job.hasBenefits) "Con seguro" else "Sin seguro",
                    color = if (job.hasBenefits) SafeGreen else DangerRed
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Accept button - Large and prominent
            Button(
                onClick = onAcceptClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (job.hasBenefits) SafeGreen else WarningOrange
                )
            ) {
                Icon(
                    imageVector = Icons.Default.CheckCircle,
                    contentDescription = null,
                    modifier = Modifier.size(28.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = "ACEPTAR CONTRATO",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
fun ImageJobCardFromDb(
    job: JobEntity,
    onAcceptClick: () -> Unit
) {
    val backgroundColor = if (job.hasBenefits && job.isSafe) SafeGreenBg else DangerRedBg
    val iconColor = if (job.hasBenefits && job.isSafe) SafeGreen else DangerRed
    val icon = if (job.hasBenefits && job.isSafe) Icons.Default.CheckCircle else Icons.Default.Warning

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = backgroundColor
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {
            // Safety indicator icon - Large and prominent
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = if (job.isSafe) "Trabajo seguro" else "Precaución",
                    tint = iconColor,
                    modifier = Modifier.size(56.dp)
                )

                // Work icon
                Icon(
                    imageVector = Icons.Default.Work,
                    contentDescription = "Trabajo",
                    tint = iconColor,
                    modifier = Modifier.size(48.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Job title - Large text
            Text(
                text = job.title,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = TextPrimary
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Pay rate - Prominent display
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.AttachMoney,
                    contentDescription = "Pago",
                    tint = iconColor,
                    modifier = Modifier.size(32.dp)
                )
                Text(
                    text = job.payRate,
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = iconColor
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Location
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.LocationOn,
                    contentDescription = "Ubicación",
                    tint = TextSecondary,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = job.location,
                    style = MaterialTheme.typography.bodyLarge,
                    color = TextSecondary
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Key job details with icons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                JobDetailIcon(
                    icon = Icons.Default.Schedule,
                    text = job.hoursPerWeek
                )
                JobDetailIcon(
                    icon = if (job.hasBenefits) Icons.Default.Lock else Icons.Default.Block,
                    text = if (job.hasBenefits) "Con seguro" else "Sin seguro",
                    color = if (job.hasBenefits) SafeGreen else DangerRed
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Accept button - Large and prominent
            Button(
                onClick = onAcceptClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (job.hasBenefits) SafeGreen else WarningOrange
                )
            ) {
                Icon(
                    imageVector = Icons.Default.CheckCircle,
                    contentDescription = null,
                    modifier = Modifier.size(28.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = "ACEPTAR CONTRATO",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
fun JobDetailIcon(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    text: String,
    color: Color = TextSecondary
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = color,
            modifier = Modifier.size(20.dp)
        )
        Spacer(modifier = Modifier.width(6.dp))
        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium,
            color = color,
            fontWeight = FontWeight.Medium
        )
    }
}

