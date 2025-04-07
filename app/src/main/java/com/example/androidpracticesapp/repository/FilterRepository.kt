package com.example.androidpracticesapp.repository

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.example.androidpracticesapp.remote.FilterPreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(name = "filters")

data class FilterSettings(val query: String, val type: String, val genre: String)

class FilterRepository(private val context: Context) {
    val filtersFlow: Flow<FilterSettings> = context.dataStore.data.map { preferences ->
        val query = preferences[FilterPreferences.KEY_SEARCH_QUERY] ?: ""
        val type = preferences[FilterPreferences.KEY_ANIME_TYPE] ?: ""
        val genre = preferences[FilterPreferences.KEY_ANIME_GENRE] ?: ""
        FilterSettings(query, type, genre)
    }

    suspend fun saveFilters(query: String, type: String, genre: String) {
        context.dataStore.edit { preferences ->
            preferences[FilterPreferences.KEY_SEARCH_QUERY] = query
            preferences[FilterPreferences.KEY_ANIME_TYPE] = type
            preferences[FilterPreferences.KEY_ANIME_GENRE] = genre
        }
    }
}
