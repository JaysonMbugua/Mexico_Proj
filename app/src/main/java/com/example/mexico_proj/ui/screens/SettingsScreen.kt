package com.example.mexico_proj.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
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
import androidx.navigation.NavHostController
import com.example.mexico_proj.AppState
import com.example.mexico_proj.MockData

@Composable
fun SettingsScreen(navController: NavHostController) {
    val currentMode = AppState.currentMode
    val user = MockData.currentUser

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(rememberScrollState())
    ) {
        // Header
        Surface(modifier = Modifier.fillMaxWidth(), color = MaterialTheme.colorScheme.primary) {
            Column(modifier = Modifier.padding(20.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(imageVector = Icons.Default.Person, contentDescription = "Perfil", tint = Color.White, modifier = Modifier.size(72.dp))
                Spacer(modifier = Modifier.height(12.dp))
                Text(text = user.name, style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold, color = Color.White)
            }
        }
        Spacer(modifier = Modifier.height(16.dp))

        // User information
        Card(modifier = Modifier.fillMaxWidth().padding(16.dp), colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)) {
            Column(modifier = Modifier.padding(20.dp)) {
                Text("Información del Usuario", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold, modifier = Modifier.padding(bottom = 16.dp))
                SettingItem(icon = Icons.Default.Person, label = "Nombre", value = user.name)
                Divider(modifier = Modifier.padding(vertical = 12.dp))
                SettingItem(icon = Icons.Default.Info, label = "Nivel de Alfabetización", value = user.literacyLevel)
            }
        }

        // App information
        Card(modifier = Modifier.fillMaxWidth().padding(16.dp), colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)) {
            Column(modifier = Modifier.padding(20.dp)) {
                Text("Configuración de la App", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold, modifier = Modifier.padding(bottom = 16.dp))
                SettingItem(icon = Icons.Default.ColorLens, label = "Modo Actual", value = when (currentMode) {
                    com.example.mexico_proj.AppMode.SPEECH_BASED -> "Basado en Voz (Azul)"
                    com.example.mexico_proj.AppMode.IMAGE_BASED -> "Basado en Imágenes (Verde)"
                    else -> ""
                })
            }
        }

        // Employer Section
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer
            )
        ) {
            Column(
                modifier = Modifier.padding(20.dp).fillMaxWidth(), // Fill width to allow centering
                horizontalAlignment = Alignment.CenterHorizontally // Center content
            ) {
                Icon(Icons.Default.Business, contentDescription = "Employer", tint = MaterialTheme.colorScheme.onPrimaryContainer, modifier = Modifier.size(48.dp))
                Spacer(modifier = Modifier.height(12.dp))
                Text("¿Eres un empleador?", style = MaterialTheme.typography.titleMedium)
                Spacer(modifier = Modifier.height(12.dp))
                Button(onClick = { navController.navigate("employer_login") }) {
                    Text("Iniciar Sesión como Empleador")
                }
            }
        }
    }
}

@Composable
fun SettingItem(icon: androidx.compose.ui.graphics.vector.ImageVector, label: String, value: String) {
    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        Icon(imageVector = icon, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(32.dp))
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Text(label, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f))
            Text(value, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)
        }
    }
}
