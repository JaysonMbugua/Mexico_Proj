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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.mexico_proj.data.AppDatabase
import com.example.mexico_proj.data.JobEntity
import com.example.mexico_proj.data.JobRepository
import com.example.mexico_proj.ui.theme.*
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddJobScreen(navController: NavController) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val database = remember { AppDatabase.getDatabase(context) }
    val repository = remember { JobRepository(database.jobDao()) }
    
    var title by remember { mutableStateOf("") }
    var payRate by remember { mutableStateOf("") }
    var location by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var hoursPerWeek by remember { mutableStateOf("") }
    var contractType by remember { mutableStateOf("") }
    var hasBenefits by remember { mutableStateOf(false) }
    var isSafe by remember { mutableStateOf(true) }
    var postedBy by remember { mutableStateOf("") }
    
    var showSuccessDialog by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(false) }
    
    val scrollState = rememberScrollState()
    
    if (showSuccessDialog) {
        AlertDialog(
            onDismissRequest = { 
                showSuccessDialog = false
                navController.popBackStack()
            },
            icon = {
                Icon(
                    Icons.Default.CheckCircle,
                    contentDescription = null,
                    tint = SafeGreen,
                    modifier = Modifier.size(48.dp)
                )
            },
            title = { 
                Text(
                    "¡Trabajo Publicado!",
                    fontWeight = FontWeight.Bold
                ) 
            },
            text = { 
                Text("Tu oferta de trabajo ha sido publicada exitosamente.") 
            },
            confirmButton = {
                Button(
                    onClick = { 
                        showSuccessDialog = false
                        navController.popBackStack()
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = SafeGreen)
                ) {
                    Text("Aceptar")
                }
            }
        )
    }

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
            Row(
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(
                        Icons.Default.ArrowBack,
                        contentDescription = "Regresar",
                        tint = Color.White
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Publicar Trabajo",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Job Title
            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Título del Trabajo *") },
                placeholder = { Text("Ej: Trabajador de Construcción") },
                leadingIcon = { Icon(Icons.Default.Work, contentDescription = null) },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
            
            // Pay Rate
            OutlinedTextField(
                value = payRate,
                onValueChange = { payRate = it },
                label = { Text("Pago *") },
                placeholder = { Text("Ej: $200 MXN/día") },
                leadingIcon = { Icon(Icons.Default.AttachMoney, contentDescription = null) },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
            
            // Location
            OutlinedTextField(
                value = location,
                onValueChange = { location = it },
                label = { Text("Ubicación *") },
                placeholder = { Text("Ej: Ciudad de México") },
                leadingIcon = { Icon(Icons.Default.LocationOn, contentDescription = null) },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
            
            // Hours per week
            OutlinedTextField(
                value = hoursPerWeek,
                onValueChange = { hoursPerWeek = it },
                label = { Text("Horas por Semana") },
                placeholder = { Text("Ej: 40 horas") },
                leadingIcon = { Icon(Icons.Default.Schedule, contentDescription = null) },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
            
            // Contract Type
            OutlinedTextField(
                value = contractType,
                onValueChange = { contractType = it },
                label = { Text("Tipo de Contrato") },
                placeholder = { Text("Ej: Permanente, Temporal") },
                leadingIcon = { Icon(Icons.Default.Description, contentDescription = null) },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
            
            // Posted By
            OutlinedTextField(
                value = postedBy,
                onValueChange = { postedBy = it },
                label = { Text("Publicado por") },
                placeholder = { Text("Nombre de la empresa") },
                leadingIcon = { Icon(Icons.Default.Business, contentDescription = null) },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
            
            // Description
            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Descripción del Trabajo *") },
                placeholder = { Text("Describe las responsabilidades y requisitos...") },
                leadingIcon = { Icon(Icons.Default.Notes, contentDescription = null) },
                modifier = Modifier.fillMaxWidth(),
                minLines = 3,
                maxLines = 5
            )
            
            // Benefits & Safety Switches
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                )
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "Características del Trabajo",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                Icons.Default.HealthAndSafety,
                                contentDescription = null,
                                tint = if (hasBenefits) SafeGreen else TextSecondary
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Incluye Beneficios/Seguro")
                        }
                        Switch(
                            checked = hasBenefits,
                            onCheckedChange = { hasBenefits = it },
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = SafeGreen,
                                checkedTrackColor = SafeGreenBg
                            )
                        )
                    }
                    
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                Icons.Default.Security,
                                contentDescription = null,
                                tint = if (isSafe) SafeGreen else DangerRed
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Ambiente de Trabajo Seguro")
                        }
                        Switch(
                            checked = isSafe,
                            onCheckedChange = { isSafe = it },
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = SafeGreen,
                                checkedTrackColor = SafeGreenBg
                            )
                        )
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            // Submit Button
            Button(
                onClick = {
                    if (title.isNotBlank() && payRate.isNotBlank() && location.isNotBlank() && description.isNotBlank()) {
                        isLoading = true
                        scope.launch {
                            val newJob = JobEntity(
                                title = title,
                                payRate = payRate,
                                location = location,
                                description = description,
                                hoursPerWeek = hoursPerWeek.ifBlank { "No especificado" },
                                contractType = contractType.ifBlank { "No especificado" },
                                hasBenefits = hasBenefits,
                                isSafe = isSafe,
                                postedBy = postedBy.ifBlank { "Empleador" }
                            )
                            repository.insertJob(newJob)
                            isLoading = false
                            showSuccessDialog = true
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                enabled = title.isNotBlank() && payRate.isNotBlank() && location.isNotBlank() && description.isNotBlank() && !isLoading,
                colors = ButtonDefaults.buttonColors(
                    containerColor = SafeGreen
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                if (isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(24.dp),
                        color = Color.White
                    )
                } else {
                    Icon(
                        Icons.Default.Publish,
                        contentDescription = null,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "PUBLICAR TRABAJO",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

