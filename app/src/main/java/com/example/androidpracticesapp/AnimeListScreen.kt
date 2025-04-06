package com.example.androidpracticesapp

import android.app.Application
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.androidpracticesapp.ui.viewmodel.AnimeListState
import com.example.androidpracticesapp.ui.viewmodel.AnimeListViewModel
import kotlinx.coroutines.launch

class AnimeListViewModelFactory(
    private val application: Application,
    private val filterRepository: FilterRepository
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AnimeListViewModel::class.java)) {
            return AnimeListViewModel(application, filterRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

@Composable
fun AnimeListScreen(
    navController: NavController,
    filterRepository: FilterRepository,
    viewModel: AnimeListViewModel = viewModel(
        factory = AnimeListViewModelFactory(
            LocalContext.current.applicationContext as Application,
            filterRepository
        )
    )
) {
    val currentFilters by filterRepository.filtersFlow.collectAsState(
        initial = FilterSettings("", "", "")
    )
    val coroutineScope = rememberCoroutineScope()
    val state by viewModel.state.collectAsState()

    Column(modifier = Modifier.fillMaxSize()) {
        if (currentFilters.query.isNotBlank() || currentFilters.type.isNotBlank() || currentFilters.genre.isNotBlank()) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Фильтры: " + listOfNotNull(
                        if (currentFilters.query.isNotBlank()) "Поиск: ${currentFilters.query}" else null,
                        if (currentFilters.type.isNotBlank()) "Тип: ${currentFilters.type}" else null,
                        if (currentFilters.genre.isNotBlank()) "Жанр: ${currentFilters.genre}" else null
                    ).joinToString(", "),
                    style = MaterialTheme.typography.bodyMedium
                )
                Button(onClick = {
                    coroutineScope.launch {
                        filterRepository.saveFilters("", "", "")
                    }
                }) {
                    Text("Сбросить")
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
        }

        when (state) {
            is AnimeListState.Loading -> {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            is AnimeListState.Error -> {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
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
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    contentPadding = PaddingValues(vertical = 8.dp)
                ) {
                    items(animeList) { anime ->
                        val animeType = anime.type
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
                            if (!animeType.isNullOrEmpty()) {
                                Text(
                                    text = animeType,
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}