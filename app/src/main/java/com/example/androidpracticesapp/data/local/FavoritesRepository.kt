package com.example.androidpracticesapp.data.local

import kotlinx.coroutines.flow.Flow

class FavoriteRepository(private val dao: FavoriteAnimeDao) {
    val favorites: Flow<List<FavoriteAnime>> = dao.getAllFavorites()

    suspend fun addFavorite(favoriteAnime: FavoriteAnime) {
        dao.insertFavorite(favoriteAnime)
    }

    suspend fun removeFavorite(favoriteAnime: FavoriteAnime) {
        dao.deleteFavorite(favoriteAnime)
    }
}
