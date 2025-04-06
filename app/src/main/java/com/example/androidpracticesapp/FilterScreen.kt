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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

@Composable
fun FilterScreen(
    filterRepository: FilterRepository,
    onFiltersApplied: () -> Unit
) {
    var searchQuery by remember { mutableStateOf("") }
    var selectedType by remember { mutableStateOf("") }
    val types = listOf("TV", "OAV")

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

        DropdownMenu(
            expanded = true,
            onDismissRequest = { }
        ) {
            types.forEach { type ->
                DropdownMenuItem(
                    text = { Text(type) },
                    onClick = { selectedType = type }
                )
            }
        }
        Text(text = "Выбранный тип: $selectedType")
        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                coroutineScope.launch {
                    filterRepository.saveFilters(searchQuery, selectedType)
                    onFiltersApplied()
                }
            },
            modifier = Modifier.align(Alignment.End)
        ) {
            Text("Готово")
        }
    }
}
