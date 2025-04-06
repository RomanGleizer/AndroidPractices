package com.example.androidpracticesapp

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(name = "filters")

class FilterRepository(private val context: Context) {
    val filtersFlow: Flow<Pair<String, String>> = context.dataStore.data
        .map { preferences ->
            val query = preferences[FilterPreferences.KEY_SEARCH_QUERY] ?: ""
            val type = preferences[FilterPreferences.KEY_ANIME_TYPE] ?: ""
            query to type
        }

    suspend fun saveFilters(query: String, type: String) {
        context.dataStore.edit { preferences ->
            preferences[FilterPreferences.KEY_SEARCH_QUERY] = query
            preferences[FilterPreferences.KEY_ANIME_TYPE] = type
        }
    }
}