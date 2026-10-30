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
import com.example.ui.components.JokeCard
import com.example.ui.viewmodel.JokeViewModel

@Composable
fun FavoritesScreen(
    viewModel: JokeViewModel,
    modifier: Modifier = Modifier
) {
    val favorites by viewModel.favoriteJokes.collectAsState()
    val playbackState by viewModel.audioPlaybackState.collectAsState()

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .testTag("favorites_screen_list"),
        contentPadding = PaddingValues(16.dp, 16.dp, 16.dp, 90.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            Column {
                Text(
                    text = "⭐ Favori Fıkralarım (${favorites.size})",
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Text(
                    text = "Kaydettiğin ve en çok güldüğün fıkralara buradan anında ulaşabilirsin.",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(top = 2.dp)
                )
            }
        }

        if (favorites.isEmpty()) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 60.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(text = "⭐", style = MaterialTheme.typography.displayLarge)
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            text = "Henüz favori fıkranız yok.",
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = "Fıkralardaki yıldız ikonuna dokunarak favorilerinize ekleyebilirsiniz.",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        } else {
            items(
                items = favorites,
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
                    onShare = { viewModel.openShareDialog(joke) }
                )
            }
        }
    }
}
