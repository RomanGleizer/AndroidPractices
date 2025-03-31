package com.example.androidpracticesapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidpracticesapp.remote.AnimeApiService
import com.example.androidpracticesapp.repository.AnimeRepositoryImpl
import com.example.androidpracticesapp.data.model.Anime
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed class AnimeDetailsState {
    data object Loading : AnimeDetailsState()
    data class Success(val anime: Anime) : AnimeDetailsState()
    data class Error(val message: String) : AnimeDetailsState()
}

class AnimeDetailsViewModel(private val animeId: Int) : ViewModel() {

    private val _state = MutableStateFlow<AnimeDetailsState>(AnimeDetailsState.Loading)
    val state: StateFlow<AnimeDetailsState> = _state.asStateFlow()

    private val repository = AnimeRepositoryImpl(AnimeApiService.create())

    init {
        fetchAnimeDetails()
    }

    private fun fetchAnimeDetails() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val anime = repository.getAnimeDetails(animeId)
                _state.value = AnimeDetailsState.Success(anime)
            } catch (e: Exception) {
                _state.value =
                    AnimeDetailsState.Error(e.localizedMessage ?: "Ошибка загрузки данных")
            }
        }
    }
}
