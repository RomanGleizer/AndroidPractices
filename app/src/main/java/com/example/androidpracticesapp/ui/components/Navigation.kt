package com.example.androidpracticesapp.ui.components

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.List
import androidx.compose.runtime.Composable
import androidx.navigation.NavController

@Composable
fun BottomNavigationBar(navController: NavController) {
    NavigationBar {
        NavigationBarItem(
            icon = { Icon(imageVector = Icons.Filled.List, contentDescription = "Список") },
            label = { Text("Список") },
            selected = false,
            onClick = { navController.navigate("list") }
        )
        NavigationBarItem(
            icon = { Icon(imageVector = Icons.Filled.FilterList, contentDescription = "Фильтр") },
            label = { Text("Фильтр") },
            selected = false,
            onClick = { navController.navigate("filter") }
        )
        NavigationBarItem(
            icon = { Icon(imageVector = Icons.Filled.Favorite, contentDescription = "Избранное") },
            label = { Text("Избранное") },
            selected = false,
            onClick = { navController.navigate("favorites") }
        )
    }
}
