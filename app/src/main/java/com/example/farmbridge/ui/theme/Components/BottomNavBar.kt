package com.example.farmbridge.ui.theme.Components


import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector


enum class BottomBarTab(val label: String, val icon: ImageVector) {
    Products("Products", Icons.Default.ShoppingCart),
    Profile("Profile", Icons.Default.Person)
}

@Composable
fun BottomNavigationBar(selectedTab: BottomBarTab, onTabSelected: (BottomBarTab) -> Unit) {
    NavigationBar {
        BottomBarTab.values().forEach { tab ->
            NavigationBarItem(
                icon = { Icon(tab.icon, contentDescription = tab.label) },
                label = { Text(tab.label) },
                selected = selectedTab == tab,
                onClick = { onTabSelected(tab) }
            )
        }
    }
}
