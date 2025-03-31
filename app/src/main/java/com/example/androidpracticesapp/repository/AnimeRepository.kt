package com.example.androidpracticesapp.repository

import com.example.androidpracticesapp.data.model.Anime

interface AnimeRepository {
    suspend fun getAnimeList(): List<Anime>
    suspend fun getAnimeDetails(animeId: Int): Anime
}