package com.example.androidpracticesapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.androidpracticesapp.cache.FilterBadgeCache
import com.example.androidpracticesapp.repository.FilterRepository
import com.example.androidpracticesapp.ui.components.AnimeDetailsScreen
import com.example.androidpracticesapp.ui.components.AnimeListScreen
import com.example.androidpracticesapp.ui.components.BottomNavigationBar
import com.example.androidpracticesapp.ui.components.FavoriteScreen
import com.example.androidpracticesapp.ui.theme.AndroidPracticesAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AndroidPracticesAppTheme {
                MainScreen()
            }
        }
    }
}


@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val context = LocalContext.current
    val filterRepository = remember { FilterRepository(context) }
    val badgeCache = remember { FilterBadgeCache() }

    Scaffold(
        bottomBar = { BottomNavigationBar(navController) }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "list",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("list") {
                AnimeListScreen(navController, filterRepository, badgeCache)
            }
            composable("filter") {
                FilterScreen(
                    filterRepository = filterRepository,
                    onFiltersApplied = { navController.popBackStack() },
                    badgeCache = badgeCache
                )
            }
            composable("favorites") {
                FavoriteScreen(navController)
            }
            composable(
                route = "details/{animeId}",
                arguments = listOf(navArgument("animeId") { type = NavType.IntType })
            ) { backStackEntry ->
                val animeId = backStackEntry.arguments?.getInt("animeId") ?: 0
                AnimeDetailsScreen(animeId)
            }
        }
    }
}
