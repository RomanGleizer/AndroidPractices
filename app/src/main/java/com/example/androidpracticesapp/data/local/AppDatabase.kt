package com.example.androidpracticesapp.data.local

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [FavoriteAnime::class], version = 1, exportSchema = false)
abstract class AnimeDatabase : RoomDatabase() {
    abstract fun favoriteAnimeDao(): FavoriteAnimeDao

    companion object {
        @Volatile
        private var INSTANCE: AnimeDatabase? = null
        private const val TAG = "AnimeDatabase"

        fun getDatabase(context: Context): AnimeDatabase {
            return INSTANCE ?: synchronized(this) {
                try {
                    val instance = Room.databaseBuilder(
                        context.applicationContext,
                        AnimeDatabase::class.java,
                        "anime_database"
                    ).build()
                    INSTANCE = instance
                    instance
                } catch (e: Exception) {
                    Log.e(TAG, "Error creating database: ${e.message}", e)
                    throw e
                }
            }
        }
    }
}
