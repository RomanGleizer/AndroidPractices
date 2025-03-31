package com.example.androidpracticesapp.repository

import com.example.androidpracticesapp.data.model.Anime
import com.example.androidpracticesapp.remote.AnimeApi

class AnimeRepositoryImpl(private val api: AnimeApi) : AnimeRepository {

    private val animeIds = listOf(52, 4657, 1234)

    override suspend fun getAnimeList(): List<Anime> {
        return animeIds.map { id -> getAnimeDetails(id) }
    }

    override suspend fun getAnimeDetails(animeId: Int): Anime {
        return api.getAnime(animeId).animeList.first()
    }
}