package com.example.androidpracticesapp.remote

import retrofit2.Retrofit
import retrofit2.converter.simplexml.SimpleXmlConverterFactory

/**
 * Сервис для создания экземпляра Retrofit, реализующего [AnimeApi].
 */
object AnimeApiService {
    private const val BASE_URL = "https://cdn.animenewsnetwork.com/"

    fun create(): AnimeApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(SimpleXmlConverterFactory.create())
            .build()
            .create(AnimeApi::class.java)
    }
}