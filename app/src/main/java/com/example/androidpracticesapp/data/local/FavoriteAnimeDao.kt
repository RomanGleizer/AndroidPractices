package com.example.androidpracticesapp.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteAnimeDao {
    @Query("SELECT * FROM favorite_anime")
    fun getAllFavorites(): Flow<List<FavoriteAnime>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(favoriteAnime: FavoriteAnime)

    @Delete
    suspend fun deleteFavorite(favoriteAnime: FavoriteAnime)
}
