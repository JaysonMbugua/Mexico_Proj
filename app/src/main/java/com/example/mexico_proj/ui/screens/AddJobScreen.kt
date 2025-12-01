package com.example.mexico_proj.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.mexico_proj.Job
import com.example.mexico_proj.MockData

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddJobScreen(navController: NavController) {
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var location by remember { mutableStateOf("") }
    var hoursPerWeek by remember { mutableStateOf("") }
    var contractType by remember { mutableStateOf("") }
    var hasBenefits by remember { mutableStateOf(false) }
    var payAmount by remember { mutableStateOf("") }
    
    val payUnits = listOf("por día", "por cubeta", "por hora", "por semana", "otro")
    var selectedPayUnit by remember { mutableStateOf(payUnits[0]) }
    var isPayUnitMenuExpanded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Publicar un Nuevo Empleo", style = MaterialTheme.typography.headlineMedium, color = MaterialTheme.colorScheme.primary)
        Spacer(modifier = Modifier.height(24.dp))

        // Job Details Card
        Card(modifier = Modifier.fillMaxWidth(), elevation = CardDefaults.cardElevation(2.dp)) {
            Column(modifier = Modifier.padding(16.dp)) {
                OutlinedTextField(value = title, onValueChange = { title = it }, label = { Text("Título del Empleo") }, leadingIcon = { Icon(Icons.Default.Title, null) }, modifier = Modifier.fillMaxWidth())
                Spacer(modifier = Modifier.height(16.dp))
                OutlinedTextField(value = description, onValueChange = { description = it }, label = { Text("Descripción del Empleo") }, leadingIcon = { Icon(Icons.Default.Description, null) }, modifier = Modifier.fillMaxWidth().height(150.dp))
            }
        }
        Spacer(modifier = Modifier.height(16.dp))

        // Payment Card
        Card(modifier = Modifier.fillMaxWidth(), elevation = CardDefaults.cardElevation(2.dp)) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                    OutlinedTextField(
                        value = payAmount, 
                        onValueChange = { payAmount = it }, 
                        label = { Text("Monto de Pago") },
                        leadingIcon = { Icon(Icons.Default.AttachMoney, null) }, 
                        modifier = Modifier.weight(1f)
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    ExposedDropdownMenuBox(expanded = isPayUnitMenuExpanded, onExpandedChange = { isPayUnitMenuExpanded = it }) {
                        OutlinedTextField(
                            value = selectedPayUnit,
                            onValueChange = {}, 
                            readOnly = true,
                            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = isPayUnitMenuExpanded) }, 
                            modifier = Modifier.menuAnchor().width(150.dp) // Fixed width for dropdown
                        )
                        ExposedDropdownMenu(expanded = isPayUnitMenuExpanded, onDismissRequest = { isPayUnitMenuExpanded = false }) {
                            payUnits.forEach { unit ->
                                DropdownMenuItem(text = { Text(unit) }, onClick = { 
                                    selectedPayUnit = unit
                                    isPayUnitMenuExpanded = false
                                })
                            }
                        }
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        
        // Scheduling & Location Card
        Card(modifier = Modifier.fillMaxWidth(), elevation = CardDefaults.cardElevation(2.dp)) {
            Column(modifier = Modifier.padding(16.dp)) {
                OutlinedTextField(value = location, onValueChange = { location = it }, label = { Text("Ubicación") }, leadingIcon = { Icon(Icons.Default.LocationOn, null) }, modifier = Modifier.fillMaxWidth())
                Spacer(modifier = Modifier.height(16.dp))
                OutlinedTextField(value = hoursPerWeek, onValueChange = { hoursPerWeek = it }, label = { Text("Horas por Semana") }, leadingIcon = { Icon(Icons.Default.Schedule, null) }, modifier = Modifier.fillMaxWidth())
                Spacer(modifier = Modifier.height(16.dp))
                OutlinedTextField(value = contractType, onValueChange = { contractType = it }, label = { Text("Tipo de Contrato") }, leadingIcon = { Icon(Icons.Default.Article, null) }, modifier = Modifier.fillMaxWidth())
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        
        // Benefits Card
        Card(modifier = Modifier.fillMaxWidth(), elevation = CardDefaults.cardElevation(2.dp)) {
            Row(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp), verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.Favorite, null, tint = MaterialTheme.colorScheme.primary)
                Spacer(modifier = Modifier.width(16.dp))
                Text("Ofrece Beneficios")
                Spacer(modifier = Modifier.weight(1f))
                Checkbox(checked = hasBenefits, onCheckedChange = { hasBenefits = it })
            }
        }
        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                if (title.isNotBlank() && description.isNotBlank()) {
                    val formattedPayRate = "$$payAmount MXN $selectedPayUnit"
                    val newJob = Job(id = MockData.jobs.size + 1, title = title, description = description, payRate = formattedPayRate, location = location, hoursPerWeek = hoursPerWeek, contractType = contractType, hasBenefits = hasBenefits, isSafe = true)
                    MockData.addJob(newJob)
                    navController.popBackStack()
                }
            },
            modifier = Modifier.fillMaxWidth().height(50.dp)
        ) {
            Text("Publicar Empleo")
        }
    }
}