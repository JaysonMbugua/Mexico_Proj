package com.example.mexico_proj.ui.screens

import androidx.compose.foundation.background
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.mexico_proj.MockData
import com.example.mexico_proj.ui.theme.*

@Composable
fun JobDetailScreen(navController: NavController, jobId: Int) {
    val job = MockData.jobs.find { it.id == jobId }
    var showAcceptDialog by remember { mutableStateOf(false) }

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
        Surface(modifier = Modifier.fillMaxWidth(), color = MaterialTheme.colorScheme.primary) {
            Column(modifier = Modifier.padding(20.dp)) {
                Text(text = job.title, style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold, color = Color.White)
                Spacer(modifier = Modifier.height(8.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.AttachMoney, contentDescription = null, tint = Color.White, modifier = Modifier.size(28.dp))
                    Text(text = formattedPay, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold, color = Color.White)
                }
            }
        }

        // Safety indicator
        val backgroundColor = if (job.hasBenefits && job.isSafe) SafeGreenBg else DangerRedBg
        val iconColor = if (job.hasBenefits && job.isSafe) SafeGreen else DangerRed
        val icon = if (job.hasBenefits && job.isSafe) Icons.Default.CheckCircle else Icons.Default.Warning

        Card(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            colors = CardDefaults.cardColors(containerColor = backgroundColor)
        ) {
            Row(
                modifier = Modifier.padding(16.dp).fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(imageVector = icon, contentDescription = null, tint = iconColor, modifier = Modifier.size(48.dp))
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = if (job.hasBenefits && job.isSafe) "✓ Trabajo seguro con beneficios" else "⚠ Precaución: Sin beneficios completos",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = iconColor
                )
            }
        }

        // Job details
        Card(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                DetailItem(icon = Icons.Default.LocationOn, label = "Ubicación", value = job.location)
                Divider(modifier = Modifier.padding(vertical = 12.dp))
                DetailItem(icon = Icons.Default.Schedule, label = "Horas por semana", value = job.hoursPerWeek)
                Divider(modifier = Modifier.padding(vertical = 12.dp))
                DetailItem(icon = Icons.Default.Description, label = "Tipo de contrato", value = job.contractType)
                Divider(modifier = Modifier.padding(vertical = 12.dp))
                DetailItem(
                    icon = if (job.hasBenefits) Icons.Default.Lock else Icons.Default.Block,
                    label = "Beneficios",
                    value = if (job.hasBenefits) "Seguro médico incluido" else "Sin seguro médico",
                    valueColor = if (job.hasBenefits) SafeGreen else DangerRed
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Description
        Card(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                Text("Descripción del Trabajo", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onPrimaryContainer)
                Spacer(modifier = Modifier.height(12.dp))
                Text(text = job.description, style = MaterialTheme.typography.bodyLarge, color = MaterialTheme.colorScheme.onPrimaryContainer)
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Accept button
        Button(
            onClick = { showAcceptDialog = true },
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp).height(64.dp),
            colors = ButtonDefaults.buttonColors(containerColor = if (job.hasBenefits) SafeGreen else WarningOrange)
        ) {
            Icon(Icons.Default.CheckCircle, contentDescription = null, modifier = Modifier.size(32.dp))
            Spacer(modifier = Modifier.width(12.dp))
            Text("ACEPTAR CONTRATO", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
        }

        Spacer(modifier = Modifier.height(24.dp))
    }

    // Accept confirmation dialog
    if (showAcceptDialog) {
        AlertDialog(
            onDismissRequest = { },
            icon = { Icon(Icons.Default.CheckCircle, contentDescription = null, modifier = Modifier.size(64.dp), tint = SafeGreen) },
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
    valueColor: Color = TextPrimary
) {
    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        Icon(imageVector = icon, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(32.dp))
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Text(label, style = MaterialTheme.typography.bodyMedium, color = TextSecondary)
            Text(value, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold, color = valueColor)
        }
    }
}
