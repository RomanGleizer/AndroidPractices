package com.example.androidpracticesapp

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.androidpracticesapp.data.local.FavoriteAnime
import com.example.androidpracticesapp.ui.viewmodel.FavoriteViewModel

@Composable
fun FavoriteScreen(navController: NavController, viewModel: FavoriteViewModel = viewModel()) {
    val favorites by viewModel.favoritesFlow.collectAsState(initial = emptyList<FavoriteAnime>())

    Column(modifier = Modifier.fillMaxSize()) {
        Text(
            text = "Избранное",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(16.dp)
        )
        if (favorites.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text("Список избранного пуст")
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(vertical = 8.dp)
            ) {
                items(favorites) { favorite ->
                    FavoriteItem(favorite) {
                        // Переход к деталям избранного (если нужно)
                        navController.navigate("details/${favorite.id}")
                    }
                }
            }
        }
    }
}

@Composable
fun FavoriteItem(favorite: FavoriteAnime, onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(16.dp)
    ) {
        Text(text = favorite.title, style = MaterialTheme.typography.titleMedium)
        favorite.type?.let {
            Text(text = it, style = MaterialTheme.typography.bodyMedium)
        }
    }
}
