package com.example.mexico_proj.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Work
import androidx.compose.material.icons.filled.AccountBalanceWallet
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.mexico_proj.AppState
import com.example.mexico_proj.MockData
import com.example.mexico_proj.UsabilityLogger

@Composable
fun ImageHomeScreen(navController: NavController) {
    LaunchedEffect(Unit) {
        UsabilityLogger.logNavigation("", "ImageHome", AppState.currentMode)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Welcome header
        Text(
            text = "Bienvenido, ${MockData.currentUser.name}",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(vertical = 24.dp)
        )

        // 2x2 Grid of icon-driven buttons
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.Center
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                ImageIconButton(
                    modifier = Modifier.weight(1f),
                    icon = Icons.Default.Work,
                    label = "Buscar Trabajo",
                    backgroundColor = MaterialTheme.colorScheme.primary,
                    onClick = {
                        UsabilityLogger.logInteraction("ICON_BUTTON", AppState.currentMode, "Buscar Trabajo")
                        navController.navigate("image_jobs")
                    }
                )

                ImageIconButton(
                    modifier = Modifier.weight(1f),
                    icon = Icons.Default.AccountBalanceWallet,
                    label = "Ver Mi Pago",
                    backgroundColor = MaterialTheme.colorScheme.secondary,
                    onClick = {
                        UsabilityLogger.logInteraction("ICON_BUTTON", AppState.currentMode, "Ver Mi Pago")
                        navController.navigate("receipt")
                    }
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                ImageIconButton(
                    modifier = Modifier.weight(1f),
                    icon = Icons.Default.CheckCircle,
                    label = "Confirmar Contrato",
                    backgroundColor = MaterialTheme.colorScheme.tertiary,
                    onClick = {
                        UsabilityLogger.logInteraction("ICON_BUTTON", AppState.currentMode, "Confirmar Contrato")
                        navController.navigate("image_jobs")
                    }
                )

                ImageIconButton(
                    modifier = Modifier.weight(1f),
                    icon = Icons.Default.Person,
                    label = "Mis Datos",
                    backgroundColor = MaterialTheme.colorScheme.secondary,
                    onClick = {
                        UsabilityLogger.logInteraction("ICON_BUTTON", AppState.currentMode, "Mis Datos")
                        navController.navigate("settings")
                    }
                )
            }
        }
    }
}

@Composable
fun ImageIconButton(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    label: String,
    backgroundColor: Color,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier
            .aspectRatio(1f)
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = backgroundColor
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = label,
                modifier = Modifier.size(64.dp),
                tint = Color.White
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = label,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                textAlign = TextAlign.Center
            )
        }
    }
}

