package com.example.mexico_proj.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.mexico_proj.MockData
import com.example.mexico_proj.TextToSpeechManager
import com.example.mexico_proj.ui.theme.*

@Composable
fun JobDetailScreen(navController: NavController, jobId: Int) {
    val job = MockData.jobs.find { it.id == jobId }
    var showAcceptDialog by remember { mutableStateOf(false) }
    
    val context = LocalContext.current
    val ttsManager = remember { TextToSpeechManager(context) }
    
    DisposableEffect(Unit) {
        onDispose {
            ttsManager.shutdown()
        }
    }

    if (job == null) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("Trabajo no encontrado")
        }
        return
    }

    // Format pay rate to include MXN
    val formattedPay = if (job.payRate.contains("MXN", ignoreCase = true)) {
        job.payRate
    } else {
        "$${job.payRate} MXN"
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(rememberScrollState())
    ) {
        // Header
        Surface(modifier = Modifier.fillMaxWidth(), color = MaterialTheme.colorScheme.primaryContainer) {
            Column(modifier = Modifier.padding(24.dp)) {
                Text(text = job.title, style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onPrimaryContainer)
                Spacer(modifier = Modifier.height(8.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.AttachMoney, contentDescription = null, tint = MaterialTheme.colorScheme.onPrimaryContainer, modifier = Modifier.size(28.dp))
                    Text(text = formattedPay, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onPrimaryContainer)
                }
            }
        }

        // Safety indicator
        // Use theme colors where possible, but keep semantic green/red for safety
        val backgroundColor = if (job.hasBenefits && job.isSafe) MaterialTheme.colorScheme.secondaryContainer else MaterialTheme.colorScheme.errorContainer
        val contentColor = if (job.hasBenefits && job.isSafe) MaterialTheme.colorScheme.onSecondaryContainer else MaterialTheme.colorScheme.onErrorContainer
        val icon = if (job.hasBenefits && job.isSafe) Icons.Default.CheckCircle else Icons.Default.Warning

        Card(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            colors = CardDefaults.cardColors(containerColor = backgroundColor)
        ) {
            Row(
                modifier = Modifier.padding(16.dp).fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(imageVector = icon, contentDescription = null, tint = contentColor, modifier = Modifier.size(48.dp))
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = if (job.hasBenefits && job.isSafe) "✓ Trabajo seguro con beneficios" else "⚠ Precaución: Sin beneficios completos",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = contentColor
                )
            }
        }

        // Job details
        Card(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
        ) {
            Column(modifier = Modifier.padding(24.dp)) {
                DetailItem(icon = Icons.Default.LocationOn, label = "Ubicación", value = job.location)
                Divider(modifier = Modifier.padding(vertical = 12.dp))
                DetailItem(icon = Icons.Default.Schedule, label = "Horas por semana", value = job.hoursPerWeek)
                Divider(modifier = Modifier.padding(vertical = 12.dp))
                DetailItem(icon = Icons.Default.Description, label = "Tipo de contrato", value = job.contractType)
                Divider(modifier = Modifier.padding(vertical = 12.dp))
                
                // Keep semantic colors for important details
                val benefitColor = if (job.hasBenefits) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.error
                DetailItem(
                    icon = if (job.hasBenefits) Icons.Default.Lock else Icons.Default.Block,
                    label = "Beneficios",
                    value = if (job.hasBenefits) "Seguro médico incluido" else "Sin seguro médico",
                    valueColor = benefitColor
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Description
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .clickable { ttsManager.speak(job.description) },
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.tertiaryContainer)
        ) {
            Column(modifier = Modifier.padding(24.dp)) {
                Text("Descripción del Trabajo", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onTertiaryContainer)
                Spacer(modifier = Modifier.height(12.dp))
                Text(text = job.description, style = MaterialTheme.typography.bodyLarge, color = MaterialTheme.colorScheme.onTertiaryContainer)
                Spacer(modifier = Modifier.height(16.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.VolumeUp, contentDescription = null, tint = MaterialTheme.colorScheme.onTertiaryContainer, modifier = Modifier.size(20.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Toca para escuchar", style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.onTertiaryContainer)
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Accept button
        Button(
            onClick = { showAcceptDialog = true },
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp).height(56.dp), // Consistent height with ReceiptScreen
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
        ) {
            Icon(Icons.Default.CheckCircle, contentDescription = null, modifier = Modifier.size(24.dp))
            Spacer(modifier = Modifier.width(12.dp))
            Text("ACEPTAR CONTRATO", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
        }

        Spacer(modifier = Modifier.height(24.dp))
    }

    // Accept confirmation dialog
    if (showAcceptDialog) {
        AlertDialog(
            onDismissRequest = { },
            icon = { Icon(Icons.Default.CheckCircle, contentDescription = null, modifier = Modifier.size(48.dp), tint = MaterialTheme.colorScheme.primary) },
            title = { Text("¡Contrato Aceptado!", textAlign = androidx.compose.ui.text.style.TextAlign.Center) },
            text = { Text("Has aceptado el trabajo de ${job.title}. Recibirás más información pronto.", textAlign = androidx.compose.ui.text.style.TextAlign.Center) },
            confirmButton = {
                TextButton(onClick = { 
                    showAcceptDialog = false
                    navController.popBackStack()
                }) {
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
    valueColor: Color = MaterialTheme.colorScheme.onSurface
) {
    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        Icon(imageVector = icon, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(32.dp))
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Text(label, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
            Text(value, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold, color = valueColor)
        }
    }
}
