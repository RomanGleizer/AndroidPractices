package com.example.androidpracticesapp.repository

import com.example.androidpracticesapp.data.model.Anime

/**
 * Интерфейс репозитория для получения данных об аниме
 */
interface AnimeRepository {
    suspend fun getAnimeList(): List<Anime>
    suspend fun getAnimeDetails(animeId: Int): Anime
}