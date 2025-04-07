package com.example.androidpracticesapp.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_anime")
data class FavoriteAnime(
    @PrimaryKey val id: Int,
    val title: String,
    val type: String?,
    val plotSummary: String
)
