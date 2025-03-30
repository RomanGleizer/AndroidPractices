package com.example.androidpracticesapp

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.androidpracticesapp.ui.viewmodel.AnimeListViewModel

@Composable
fun AnimeDetailsScreen(
    animeId: Int,
    viewModel: AnimeListViewModel = viewModel()
) {
    val animeList by viewModel.animeList.collectAsState()
    val anime = animeList.find { it.id == animeId }

    if (anime != null) {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            val (titleRef, typeRef, plotRef) = createRefs()
            Text(
                text = anime.title,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.constrainAs(titleRef) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                }
            )
            if (!anime.type.isNullOrEmpty()) {
                Text(
                    text = "Type: ${anime.type}",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.constrainAs(typeRef) {
                        top.linkTo(titleRef.bottom, margin = 8.dp)
                        start.linkTo(parent.start)
                    }
                )
            }
            Text(
                text = anime.plotSummary,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.constrainAs(plotRef) {
                    top.linkTo(typeRef.bottom, margin = 16.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
            )
        }
    } else {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Элемент не найден",
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}
