package com.example.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.ChatBubbleOutline
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.GraphicEq
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material.icons.filled.Stop
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.JokeEntity
import com.example.ui.theme.AmberPrimary
import com.example.ui.theme.CoralAccent
import com.example.ui.theme.HeartRed
import com.example.ui.theme.StarGold

@Composable
fun JokeCard(
    joke: JokeEntity,
    isSpeaking: Boolean,
    onPlayAudio: () -> Unit,
    onStopAudio: () -> Unit,
    onPlayRimshot: () -> Unit,
    onToggleFavorite: () -> Unit,
    onToggleLike: () -> Unit,
    onOpenComments: () -> Unit,
    onShare: () -> Unit,
    modifier: Modifier = Modifier
) {
    var isExpanded by remember { mutableStateOf(false) }
    var showThinking by remember { mutableStateOf(false) }

    val isLongContent = joke.content.length > 280
    val displayContent = if (!isExpanded && isLongContent) {
        joke.content.take(260) + "..."
    } else {
        joke.content
    }

    val categoryColor = when (joke.category) {
        "Nasreddin Hoca" -> Color(0xFF2E7D32)
        "Temel ile Dursun" -> Color(0xFF0288D1)
        "Bektaşi" -> Color(0xFF7B1FA2)
        "Okul & Öğretmen" -> Color(0xFFE65100)
        "Doktor & Hasta" -> Color(0xFFC2185B)
        "Modern Hayat" -> Color(0xFF00897B)
        "Yapay Zekâ" -> Color(0xFF5E35B1)
        else -> AmberPrimary
    }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .testTag("joke_card_${joke.id}"),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // Header: Category chip, Author & Badges
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Surface(
                        color = categoryColor.copy(alpha = 0.12f),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(
                            text = joke.category,
                            style = MaterialTheme.typography.labelMedium,
                            color = categoryColor,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }

                    if (joke.isAiGenerated) {
                        Spacer(modifier = Modifier.width(6.dp))
                        Surface(
                            color = CoralAccent.copy(alpha = 0.15f),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 3.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Default.AutoAwesome,
                                    contentDescription = "Yapay Zeka",
                                    tint = CoralAccent,
                                    modifier = Modifier.size(12.dp)
                                )
                                Spacer(modifier = Modifier.width(3.dp))
                                Text(
                                    text = if (joke.aiModelUsed?.contains("argon") == true) "Gemini 4 Argon" else "Gemini 3.8",
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = CoralAccent
                                )
                            }
                        }
                    }
                }

                // Favorite Toggle Button
                IconButton(
                    onClick = onToggleFavorite,
                    modifier = Modifier
                        .size(36.dp)
                        .testTag("favorite_btn_${joke.id}")
                ) {
                    Icon(
                        imageVector = if (joke.isFavorite) Icons.Default.Star else Icons.Default.StarBorder,
                        contentDescription = "Favoriye Ekle",
                        tint = if (joke.isFavorite) StarGold else MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Joke Title
            Text(
                text = joke.title,
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onSurface,
                fontWeight = FontWeight.Bold
            )

            // Author and signature
            Text(
                text = "Yazar: ${joke.authorName}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(top = 2.dp, bottom = 8.dp)
            )

            // Joke Content
            Text(
                text = displayContent,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface,
                lineHeight = 24.sp
            )

            if (isLongContent) {
                Row(
                    modifier = Modifier
                        .clickable { isExpanded = !isExpanded }
                        .padding(vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = if (isExpanded) "Daha az göster" else "Devamını oku...",
                        color = AmberPrimary,
                        style = MaterialTheme.typography.labelLarge
                    )
                    Icon(
                        imageVector = if (isExpanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                        contentDescription = null,
                        tint = AmberPrimary,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }

            // High Thinking Reasoning Box (if available from Gemini 4 Argon)
            if (!joke.thinkingAnalysis.isNullOrBlank()) {
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedCard(
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.outlinedCardColors(
                        containerColor = CoralAccent.copy(alpha = 0.05f)
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { showThinking = !showThinking }
                ) {
                    Column(modifier = Modifier.padding(10.dp)) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.Lightbulb,
                                    contentDescription = null,
                                    tint = CoralAccent,
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = "Mizah Analizi (Gemini Derin Düşünce)",
                                    style = MaterialTheme.typography.labelMedium,
                                    color = CoralAccent,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                            Icon(
                                imageVector = if (showThinking) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                                contentDescription = null,
                                tint = CoralAccent,
                                modifier = Modifier.size(16.dp)
                            )
                        }

                        AnimatedVisibility(visible = showThinking) {
                            Text(
                                text = joke.thinkingAnalysis,
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                modifier = Modifier.padding(top = 6.dp)
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Action Row: Audio TTS, Rimshot effect, Likes, Comments, Share
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Audio TTS Listen Button with wave animation
                FilledTonalButton(
                    onClick = {
                        if (isSpeaking) onStopAudio() else onPlayAudio()
                    },
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.testTag("listen_btn_${joke.id}")
                ) {
                    if (isSpeaking) {
                        LiveAudioWaveIndicator()
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Durdur", style = MaterialTheme.typography.labelMedium)
                    } else {
                        Icon(
                            imageVector = Icons.Default.PlayArrow,
                            contentDescription = "Sesli Dinle",
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Sesli Dinle", style = MaterialTheme.typography.labelMedium)
                    }
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    // Dım-tıs punchline sound effect
                    FilledTonalIconButton(
                        onClick = onPlayRimshot,
                        modifier = Modifier
                            .size(38.dp)
                            .testTag("rimshot_btn_${joke.id}")
                    ) {
                        Text("🥁", fontSize = 16.sp)
                    }

                    Spacer(modifier = Modifier.width(4.dp))

                    // Likes button
                    Row(
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .clickable { onToggleLike() }
                            .padding(horizontal = 6.dp, vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = if (joke.isLikedByUser) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                            contentDescription = "Beğen",
                            tint = if (joke.isLikedByUser) HeartRed else MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "${joke.likesCount}",
                            style = MaterialTheme.typography.labelMedium,
                            color = if (joke.isLikedByUser) HeartRed else MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    Spacer(modifier = Modifier.width(4.dp))

                    // Comments button
                    IconButton(
                        onClick = onOpenComments,
                        modifier = Modifier
                            .size(38.dp)
                            .testTag("comments_btn_${joke.id}")
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.ChatBubbleOutline,
                                contentDescription = "Yorumlar",
                                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                                modifier = Modifier.size(19.dp)
                            )
                            if (joke.commentsCount > 0) {
                                Spacer(modifier = Modifier.width(2.dp))
                                Text(
                                    text = "${joke.commentsCount}",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                    }

                    // Share button
                    IconButton(
                        onClick = onShare,
                        modifier = Modifier
                            .size(38.dp)
                            .testTag("share_btn_${joke.id}")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Share,
                            contentDescription = "Paylaş",
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(19.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun LiveAudioWaveIndicator() {
    val infiniteTransition = rememberInfiniteTransition(label = "wave")
    val alpha by infiniteTransition.animateFloat(
        initialValue = 0.3f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(400),
            repeatMode = RepeatMode.Reverse
        ),
        label = "alpha"
    )

    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(
            imageVector = Icons.Default.GraphicEq,
            contentDescription = "Oynatılıyor",
            tint = AmberPrimary.copy(alpha = alpha),
            modifier = Modifier.size(18.dp)
        )
    }
}
