package com.example.androidpracticesapp.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidpracticesapp.remote.AnimeApiService
import com.example.androidpracticesapp.repository.AnimeRepositoryImpl
import com.example.androidpracticesapp.repository.FilterRepository
import com.example.androidpracticesapp.data.model.Anime
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed class AnimeListState {
    object Loading : AnimeListState()
    data class Success(val animeList: List<Anime>) : AnimeListState()
    data class Error(val message: String) : AnimeListState()
}

class AnimeListViewModel(
    application: Application,
    private val filterRepository: FilterRepository
) : AndroidViewModel(application) {

    private val repository = AnimeRepositoryImpl(AnimeApiService.create())
    private val _state = MutableStateFlow<AnimeListState>(AnimeListState.Loading)
    val state: StateFlow<AnimeListState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            filterRepository.filtersFlow.collect { filterSettings ->
                fetchAnimeList(filterSettings.query, filterSettings.type, filterSettings.genre)
            }
        }
    }

    private fun fetchAnimeList(query: String, type: String, genre: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val animeList = repository.getAnimeList()
                val filteredList = animeList.filter { anime ->
                    (query.isBlank() || anime.title.contains(query, ignoreCase = true)) &&
                            (type.isBlank() || anime.type == type) &&
                            (genre.isBlank() || anime.info.any {
                                it.type.equals("Genres", ignoreCase = true) &&
                                        it.text.contains(genre, ignoreCase = true)
                            })
                }
                _state.value = AnimeListState.Success(filteredList)
            } catch (e: Exception) {
                _state.value = AnimeListState.Error(e.localizedMessage ?: "Ошибка загрузки данных")
            }
        }
    }
}
