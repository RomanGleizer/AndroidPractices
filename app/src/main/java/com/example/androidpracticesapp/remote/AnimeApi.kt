package com.example.androidpracticesapp.remote

import com.example.androidpracticesapp.data.model.AnimeResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface AnimeApi {
    @GET("encyclopedia/api.xml")
    suspend fun getAnime(@Query("anime") animeId: Int): AnimeResponse
}