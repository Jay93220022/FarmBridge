package com.example.farmbridge.ui.theme.Components

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter

data class BottomNavItem(val name: String, val icon: Painter, val route: String)

@Composable
fun BottomBar(
    items: List<BottomNavItem>,
    onItemClick: (BottomNavItem) -> Unit
) {
    NavigationBar {
        items.forEach { item ->
            NavigationBarItem(
                icon = { Icon(item.icon, contentDescription = item.name) },
                label = { Text(item.name) },
                selected = false,
                onClick = { onItemClick(item) }
            )
        }
    }
}
