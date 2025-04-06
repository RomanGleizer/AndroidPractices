package com.example.androidpracticesapp

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.androidpracticesapp.repository.FilterRepository
import kotlinx.coroutines.launch

@Composable
fun FilterScreen(
    filterRepository: FilterRepository,
    onFiltersApplied: () -> Unit
) {
    var searchQuery by remember { mutableStateOf("") }
    var selectedType by remember { mutableStateOf("") }
    var selectedGenre by remember { mutableStateOf("") }

    val types = listOf("TV", "OAV")
    val genres = listOf("comedy", "horror", "mystery", "supernatural")

    var typeExpanded by remember { mutableStateOf(false) }
    var genreExpanded by remember { mutableStateOf(false) }

    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(text = "Настройки фильтрации", style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            label = { Text("Поиск по названию") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = { typeExpanded = true },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = if (selectedType.isEmpty()) "Выберите тип" else "Тип: $selectedType")
        }
        DropdownMenu(
            expanded = typeExpanded,
            onDismissRequest = { typeExpanded = false }
        ) {
            types.forEach { type ->
                DropdownMenuItem(
                    text = { Text(type) },
                    onClick = {
                        selectedType = type
                        typeExpanded = false
                    }
                )
            }
        }
        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = { genreExpanded = true },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = if (selectedGenre.isEmpty()) "Выберите жанр" else "Жанр: $selectedGenre")
        }
        DropdownMenu(
            expanded = genreExpanded,
            onDismissRequest = { genreExpanded = false }
        ) {
            genres.forEach { genre ->
                DropdownMenuItem(
                    text = { Text(genre) },
                    onClick = {
                        selectedGenre = genre
                        genreExpanded = false
                    }
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                coroutineScope.launch {
                    filterRepository.saveFilters(searchQuery, selectedType, selectedGenre)
                    onFiltersApplied()
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Готово")
        }
    }
}
