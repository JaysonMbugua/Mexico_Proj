package com.example.mexico_proj.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.mexico_proj.*

@Composable
fun SettingsScreen(navController: NavHostController) {
    val settings = SettingsManager.settings

    Column(Modifier.padding(20.dp)) {
        Text("Settings", style = MaterialTheme.typography.headlineMedium)

        Spacer(Modifier.height(20.dp))

        Row(
            Modifier
                .fillMaxWidth()
                .clickable { navController.navigate("language") }
                .padding(12.dp)
        ) {
            Text("Language", style = MaterialTheme.typography.bodyLarge)
            Spacer(Modifier.weight(1f))
            Text(settings.language.name)
        }
    }
}
