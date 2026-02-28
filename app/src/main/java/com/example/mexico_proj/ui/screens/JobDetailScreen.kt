package com.example.mexico_proj.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Block
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
import kotlinx.coroutines.launch

@Composable
fun JobDetailScreen(navController: NavController, jobId: Int) {
    val context = LocalContext.current
    val database = remember { AppDatabase.getDatabase(context) }
    val scope = rememberCoroutineScope()
    
    var job by remember { mutableStateOf<JobEntity?>(null) }
    val currentMode = AppState.currentMode
    var showAcceptDialog by remember { mutableStateOf(false) }

    LaunchedEffect(jobId) {
        UsabilityLogger.logNavigation("JobList", "JobDetail", currentMode)
        job = database.jobDao().getJobById(jobId)
    }

    if (job == null) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
        return
    }
    
    val currentJob = job!!

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(rememberScrollState())
    ) {
        // Header
        Surface(
            modifier = Modifier.fillMaxWidth(),
            color = MaterialTheme.colorScheme.primary
        ) {
            Column(
                modifier = Modifier.padding(20.dp)
            ) {
                Text(
                    text = currentJob.title,
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        Icons.Default.AttachMoney,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(28.dp)
                    )
                    Text(
                        text = currentJob.payRate,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
            }
        }

        // Safety indicator
        val backgroundColor = if (currentJob.hasBenefits && currentJob.isSafe) SafeGreenBg else DangerRedBg
        val iconColor = if (currentJob.hasBenefits && currentJob.isSafe) SafeGreen else DangerRed
        val icon = if (currentJob.hasBenefits && currentJob.isSafe) Icons.Default.CheckCircle else Icons.Default.Warning

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = backgroundColor
            )
        ) {
            Row(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = iconColor,
                    modifier = Modifier.size(48.dp)
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = if (currentJob.hasBenefits && currentJob.isSafe)
                        "✓ Trabajo seguro con beneficios"
                    else
                        "⚠ Precaución: Sin beneficios completos",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = iconColor
                )
            }
        }

        // Job details
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            )
        ) {
            Column(
                modifier = Modifier.padding(20.dp)
            ) {
                DetailItem(
                    icon = Icons.Default.LocationOn,
                    label = "Ubicación",
                    value = currentJob.location
                )
                HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp))

                DetailItem(
                    icon = Icons.Default.Schedule,
                    label = "Horas por semana",
                    value = currentJob.hoursPerWeek
                )
                HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp))

                DetailItem(
                    icon = Icons.Default.Description,
                    label = "Tipo de contrato",
                    value = currentJob.contractType
                )
                HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp))

                DetailItem(
                    icon = if (currentJob.hasBenefits) Icons.Default.Lock else Icons.Default.Block,
                    label = "Beneficios",
                    value = if (currentJob.hasBenefits) "Seguro médico incluido" else "Sin seguro médico",
                    valueColor = if (currentJob.hasBenefits) SafeGreen else DangerRed
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Description
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer
            )
        ) {
            Column(
                modifier = Modifier.padding(20.dp)
            ) {
                Text(
                    text = "Descripción del Trabajo",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = currentJob.description,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Accept button
        Button(
            onClick = {
                showAcceptDialog = true
                UsabilityLogger.completeTask("TASK3_ACCEPT_CONTRACT", currentMode, "Contract accepted: ${currentJob.title}")
                UsabilityLogger.logInteraction("CONTRACT_ACCEPT", currentMode, "Job: ${currentJob.title}")
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .height(64.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = if (currentJob.hasBenefits) SafeGreen else WarningOrange
            )
        ) {
            Icon(
                Icons.Default.CheckCircle,
                contentDescription = null,
                modifier = Modifier.size(32.dp)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = "ACEPTAR CONTRATO",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(24.dp))
    }

    // Accept confirmation dialog
    if (showAcceptDialog) {
        AlertDialog(
            onDismissRequest = { },
            icon = {
                Icon(
                    Icons.Default.CheckCircle,
                    contentDescription = null,
                    modifier = Modifier.size(64.dp),
                    tint = SafeGreen
                )
            },
            title = {
                Text(
                    "¡Contrato Aceptado!",
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center
                )
            },
            text = {
                Text(
                    "Has aceptado el trabajo de ${currentJob.title}. Recibirás más información pronto.",
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center
                )
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        showAcceptDialog = false
                        navController.popBackStack()
                    }
                ) {
                    Text("Entendido")
                }
            }
        )
    }
}

@Composable
fun DetailItem(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    label: String,
    value: String,
    valueColor: Color = TextPrimary
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(32.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Text(
                text = label,
                style = MaterialTheme.typography.bodyMedium,
                color = TextSecondary
            )
            Text(
                text = value,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                color = valueColor
            )
        }
    }
}

