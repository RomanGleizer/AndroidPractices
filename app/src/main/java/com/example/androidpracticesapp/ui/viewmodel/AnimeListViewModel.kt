package com.example.androidpracticesapp.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidpracticesapp.remote.AnimeApiService
import com.example.androidpracticesapp.repository.AnimeRepositoryImpl
import com.example.androidpracticesapp.FilterRepository
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

class AnimeListViewModel(application: Application) : AndroidViewModel(application) {

    private val filterRepository = FilterRepository(application.applicationContext)
    private val repository = AnimeRepositoryImpl(AnimeApiService.create())

    private val _state = MutableStateFlow<AnimeListState>(AnimeListState.Loading)
    val state: StateFlow<AnimeListState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            filterRepository.filtersFlow.collect { (query, type) ->
                fetchAnimeList(query, type)
            }
        }
    }

    private fun fetchAnimeList(query: String, type: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val animeList = repository.getAnimeList()
                val filteredList = animeList.filter { anime ->
                    (query.isBlank() || anime.title.contains(query, ignoreCase = true)) &&
                            (type.isBlank() || anime.type == type)
                }
                _state.value = AnimeListState.Success(filteredList)
            } catch (e: Exception) {
                _state.value = AnimeListState.Error(e.localizedMessage ?: "Ошибка загрузки данных")
            }
        }
    }
}
