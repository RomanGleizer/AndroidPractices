package com.example.androidpracticesapp

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.androidpracticesapp.ui.viewmodel.AnimeListState
import com.example.androidpracticesapp.ui.viewmodel.AnimeListViewModel

@Composable
fun AnimeListScreen(
    navController: NavController,
    viewModel: AnimeListViewModel = viewModel()
) {
    val state by viewModel.state.collectAsState()

    when (state) {
        is AnimeListState.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        is AnimeListState.Error -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = (state as AnimeListState.Error).message,
                    style = MaterialTheme.typography.titleMedium
                )
            }
        }

        is AnimeListState.Success -> {
            val animeList = (state as AnimeListState.Success).animeList
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
                            val typeValue = anime.type
                            if (!typeValue.isNullOrEmpty()) {
                                Text(
                                    text = typeValue,
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            }
                        }

                    }
                    Divider()
                }
            }
        }
    }
}
