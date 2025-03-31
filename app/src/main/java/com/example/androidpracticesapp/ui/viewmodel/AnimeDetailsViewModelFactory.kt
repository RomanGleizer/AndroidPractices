package com.example.androidpracticesapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class AnimeDetailsViewModelFactory(private val animeId: Int) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AnimeDetailsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AnimeDetailsViewModel(animeId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
