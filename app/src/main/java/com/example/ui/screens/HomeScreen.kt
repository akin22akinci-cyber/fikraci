package com.example.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.example.ui.components.CategoryChipsRow
import com.example.ui.components.HeroBanner
import com.example.ui.components.JokeCard
import com.example.ui.components.SearchBarAndSort
import com.example.ui.viewmodel.JokeViewModel

@Composable
fun HomeScreen(
    viewModel: JokeViewModel,
    modifier: Modifier = Modifier
) {
    val jokes by viewModel.jokes.collectAsState()
    val selectedCategory by viewModel.selectedCategory.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()
    val sortMode by viewModel.sortMode.collectAsState()
    val playbackState by viewModel.audioPlaybackState.collectAsState()

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .testTag("home_screen_feed"),
        contentPadding = PaddingValues(bottom = 90.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        // Hero Banner
        item {
            HeroBanner(
                onSurpriseJoke = { viewModel.triggerSurpriseJoke() },
                onNewJoke = { viewModel.openNewJokeDialog() },
                modifier = Modifier.padding(top = 8.dp, start = 16.dp, end = 16.dp)
            )
        }

        // Category Selection
        item {
            CategoryChipsRow(
                categories = viewModel.allCategories,
                selectedCategory = selectedCategory,
                onCategorySelected = { viewModel.selectCategory(it) }
            )
        }

        // Search & Sort bar
        item {
            SearchBarAndSort(
                searchQuery = searchQuery,
                onSearchQueryChange = { viewModel.updateSearchQuery(it) },
                currentSortMode = sortMode,
                onSortModeChange = { viewModel.setSortMode(it) }
            )
        }

        // Section Title & count
        item {
            Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                Text(
                    text = if (selectedCategory == "Tümü") "Tüm Fıkralar (${jokes.size})" else "$selectedCategory Fıkraları (${jokes.size})",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
        }

        // Feed
        if (jokes.isEmpty()) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(40.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(text = "🔍", style = MaterialTheme.typography.displayMedium)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Aramanıza uygun fıkra bulunamadı.",
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Text(
                            text = "Farklı bir kelime deneyin veya yeni bir fıkra ekleyin!",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        } else {
            items(
                items = jokes,
                key = { it.id }
            ) { joke ->
                val isSpeakingThis = playbackState.isSpeaking && playbackState.currentJokeId == joke.id
                JokeCard(
                    joke = joke,
                    isSpeaking = isSpeakingThis,
                    onPlayAudio = { viewModel.playJokeAudio(joke) },
                    onStopAudio = { viewModel.stopAudio() },
                    onPlayRimshot = { viewModel.playRimshotEffect() },
                    onToggleFavorite = { viewModel.toggleFavorite(joke) },
                    onToggleLike = { viewModel.toggleLike(joke) },
                    onOpenComments = { viewModel.openComments(joke) },
                    onShare = { viewModel.openShareDialog(joke) },
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }
        }
    }
}
