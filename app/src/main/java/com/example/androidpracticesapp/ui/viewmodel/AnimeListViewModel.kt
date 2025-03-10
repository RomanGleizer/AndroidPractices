package com.example.androidpracticesapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import com.example.androidpracticesapp.data.model.Anime

class AnimeListViewModel : ViewModel() {

    private val _animeList = MutableStateFlow<List<Anime>>(emptyList())
    val animeList: StateFlow<List<Anime>> = _animeList.asStateFlow()

    init {
        _animeList.value = listOf(
            Anime(
                id = 4657,
                title = "Lesson XX",
                type = "OAV",
                plotSummary = "" +
                        "The story of Lesson XX has a sweet feel to it. " +
                        "It revolves around two boys, " +
                        "Shizuka and Sakura who are friends at a coed boarding school. " +
                        "One day, Shizuka notices Sakura’s beauty " +
                        "when he dives in to take a hit from a baseball. " +
                        "After some mixed up thoughts, he jokes about kissing him, " +
                        "but Sakura insists on a kiss under the starry sky. " +
                        "Afterwards, Shizuka thinks it’s best for them to be apart " +
                        "to sort out their thoughts and feelings. " +
                        "A classic shounen ai/yaoi with love, misunderstanding, violence and a sweet end." +
                        ""
            )
        )
    }
}
