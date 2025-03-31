package com.example.androidpracticesapp

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.androidpracticesapp.ui.viewmodel.AnimeDetailsState
import com.example.androidpracticesapp.ui.viewmodel.AnimeDetailsViewModel
import com.example.androidpracticesapp.ui.viewmodel.AnimeDetailsViewModelFactory

@Composable
fun AnimeDetailsScreen(animeId: Int) {
    val owner = LocalViewModelStoreOwner.current
    val viewModel: AnimeDetailsViewModel = viewModel(
        viewModelStoreOwner = owner!!,
        factory = AnimeDetailsViewModelFactory(animeId)
    )
    val state by viewModel.state.collectAsState()

    when (state) {
        is AnimeDetailsState.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        is AnimeDetailsState.Error -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = (state as AnimeDetailsState.Error).message,
                    style = MaterialTheme.typography.titleMedium
                )
            }
        }

        is AnimeDetailsState.Success -> {
            val anime = (state as AnimeDetailsState.Success).anime
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
        }
    }
}
