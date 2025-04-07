package com.example.androidpracticesapp.remote

import androidx.datastore.preferences.core.stringPreferencesKey

object FilterPreferences {
    val KEY_SEARCH_QUERY = stringPreferencesKey("search_query")
    val KEY_ANIME_TYPE = stringPreferencesKey("anime_type")
    val KEY_ANIME_GENRE = stringPreferencesKey("anime_genre")
}
