package com.example.mexico_proj.ui.components

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun AppBottomBar(navController: NavHostController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()

    NavigationBar {
        BottomTab.tabs.forEach { tab ->
            val isSelected = navBackStackEntry?.destination?.route == tab.route

            NavigationBarItem(
                selected = isSelected,
                onClick = { navController.navigate(tab.route) },
                icon = { Icon(tab.icon, contentDescription = tab.label) }
                ,
                label = { Text(tab.label) }
            )
        }
    }
}
