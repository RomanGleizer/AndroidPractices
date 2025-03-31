package com.example.androidpracticesapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidpracticesapp.remote.AnimeApiService
import com.example.androidpracticesapp.repository.AnimeRepositoryImpl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import com.example.androidpracticesapp.data.model.Anime
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Состояния загрузки данных для списка аниме
 */
sealed class AnimeListState {
    data object Loading : AnimeListState()
    data class Success(val animeList: List<Anime>) : AnimeListState()
    data class Error(val message: String) : AnimeListState()
}

/**
 * ViewModel для экрана списка аниме
 */
class AnimeListViewModel : ViewModel() {

    private val _state = MutableStateFlow<AnimeListState>(AnimeListState.Loading)
    val state: StateFlow<AnimeListState> = _state.asStateFlow()

    private val repository = AnimeRepositoryImpl(AnimeApiService.create())

    init {
        fetchAnimeList()
    }

    private fun fetchAnimeList() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val animeList = repository.getAnimeList()
                _state.value = AnimeListState.Success(animeList)
            } catch (e: Exception) {
                _state.value = AnimeListState.Error(e.localizedMessage ?: "Ошибка загрузки данных")
            }
        }
    }
}
