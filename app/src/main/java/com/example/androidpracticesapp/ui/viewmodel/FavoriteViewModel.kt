package com.example.androidpracticesapp.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidpracticesapp.data.local.FavoriteRepository
import com.example.androidpracticesapp.data.local.AnimeDatabase
import com.example.androidpracticesapp.data.local.FavoriteAnime
import com.example.androidpracticesapp.data.model.Anime
import kotlinx.coroutines.launch

class FavoriteViewModel(application: Application) : AndroidViewModel(application) {
    private val dao = AnimeDatabase.getDatabase(application).favoriteAnimeDao()
    private val repository = FavoriteRepository(dao)

    val favoritesFlow = repository.favorites

    fun addFavorite(anime: Anime) {
        val favorite = FavoriteAnime(
            id = anime.id,
            title = anime.title,
            type = anime.type,
            plotSummary = anime.plotSummary
        )
        viewModelScope.launch {
            repository.addFavorite(favorite)
        }
    }

    fun removeFavorite(anime: Anime) {
        val favorite = FavoriteAnime(
            id = anime.id,
            title = anime.title,
            type = anime.type,
            plotSummary = anime.plotSummary
        )
        viewModelScope.launch {
            repository.removeFavorite(favorite)
        }
    }
}
