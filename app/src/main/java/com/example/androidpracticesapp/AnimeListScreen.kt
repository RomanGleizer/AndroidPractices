package com.example.androidpracticesapp

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.androidpracticesapp.ui.viewmodel.AnimeListViewModel

@Composable
fun AnimeListScreen(
    navController: NavController,
    viewModel: AnimeListViewModel = viewModel()
) {
    val animeList by viewModel.animeList.collectAsState()
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(vertical = 8.dp)
    ) {
        items(animeList) { anime ->
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { navController.navigate("details/${anime.id}") }
                    .padding(16.dp)
            ) {
                Text(
                    text = anime.title,
                    style = MaterialTheme.typography.titleMedium
                )
                if (!anime.type.isNullOrEmpty()) {
                    Text(
                        text = anime.type,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
            HorizontalDivider()
        }
    }
}
