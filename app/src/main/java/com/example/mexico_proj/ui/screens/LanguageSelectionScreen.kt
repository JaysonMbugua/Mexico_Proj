package com.example.mexico_proj.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.mexico_proj.*

@Composable
fun LanguageSelectionScreen(navController: NavHostController) {
    val settings = SettingsManager.settings

    Column(Modifier.padding(20.dp)) {
        Text("Choose Language", style = MaterialTheme.typography.headlineMedium)

        Spacer(Modifier.height(20.dp))

        LanguageOption(
            text = "English",
            selected = settings.language == AppLanguage.ENGLISH,
            onClick = {
                settings.language = AppLanguage.ENGLISH
                navController.popBackStack()
            }
        )

        LanguageOption(
            text = "Spanish",
            selected = settings.language == AppLanguage.SPANISH,
            onClick = {
                settings.language = AppLanguage.SPANISH
                navController.popBackStack()
            }
        )
    }
}

@Composable
fun LanguageOption(text: String, selected: Boolean, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(12.dp)
    ) {
        Text(text, style = MaterialTheme.typography.bodyLarge)
        Spacer(Modifier.weight(1f))
        if (selected) Text("✓")
    }
}

