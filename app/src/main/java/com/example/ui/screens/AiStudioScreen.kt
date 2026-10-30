package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Publish
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Stop
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.JokeEntity
import com.example.ui.theme.AmberPrimary
import com.example.ui.theme.CoralAccent
import com.example.ui.theme.LaughYellow
import com.example.ui.viewmodel.JokeViewModel

@Composable
fun AiStudioScreen(
    viewModel: JokeViewModel,
    modifier: Modifier = Modifier
) {
    val isGenerating by viewModel.isGeneratingAi.collectAsState()
    val generatedJoke by viewModel.generatedJoke.collectAsState()
    val aiStatusMessage by viewModel.aiStatusMessage.collectAsState()
    val useHighThinking by viewModel.useHighThinking.collectAsState()
    val playbackState by viewModel.audioPlaybackState.collectAsState()

    var customTopic by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf("Temel ile Dursun") }

    val quickInspirations = listOf(
        "Temel ve Akıllı Telefon",
        "Nasreddin Hoca ve Robot Eşek",
        "Doktor Muayenesinde Yapay Zekâ",
        "Öğretmen ve Sınav Sorusu",
        "Kahvehanede Wi-Fi Sohbeti"
    )

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .testTag("ai_studio_screen"),
        contentPadding = PaddingValues(16.dp, 16.dp, 16.dp, 90.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Banner Header
        item {
            Card(
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(
                    containerColor = CoralAccent.copy(alpha = 0.12f)
                ),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(48.dp)
                            .clip(CircleShape)
                            .background(CoralAccent),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.AutoAwesome,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text(
                            text = "Yapay Zekâ Fıkra Atölyesi",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = "İstediğin konuyu veya karakteri söyle, yapay zekâ senin için kahkaha dolu bir fıkra yazsın!",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }

        // Mode Selector: Gemini 4 Argon High Thinking vs Gemini 3.8 Flash
        item {
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.weight(1f)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .clip(RoundedCornerShape(10.dp))
                                .background(if (useHighThinking) Color(0xFF6200EA) else AmberPrimary),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = if (useHighThinking) Icons.Default.Psychology else Icons.Default.AutoAwesome,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.size(22.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Text(
                                text = if (useHighThinking) "Fıkra Ustası (Derin Düşünce)" else "Hızlı Fıkra Modu",
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = if (useHighThinking) "Gemini 4 Argon (High Thinking) ile zekice kurgulanmış espriler" else "Gemini 3.8 Flash ile anında pratik mizah",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }

                    Switch(
                        checked = useHighThinking,
                        onCheckedChange = { viewModel.toggleHighThinking(it) },
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = Color.White,
                            checkedTrackColor = Color(0xFF6200EA)
                        ),
                        modifier = Modifier.testTag("high_thinking_toggle")
                    )
                }
            }
        }

        // Category Choice
        item {
            Column {
                Text(
                    text = "Kategori",
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 6.dp)
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    val availableCats = listOf(
                        "Nasreddin Hoca",
                        "Temel ile Dursun",
                        "Bektaşi",
                        "Okul & Öğretmen",
                        "Doktor & Hasta",
                        "Modern Hayat",
                        "Yapay Zekâ"
                    )
                    availableCats.forEach { cat ->
                        val isSelected = cat == selectedCategory
                        FilterChip(
                            selected = isSelected,
                            onClick = { selectedCategory = cat },
                            label = { Text(cat, style = MaterialTheme.typography.labelSmall) },
                            shape = RoundedCornerShape(10.dp),
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = AmberPrimary,
                                selectedLabelColor = MaterialTheme.colorScheme.onPrimary
                            ),
                            border = null
                        )
                    }
                }
            }
        }

        // Topic / Prompt Input
        item {
            Column {
                Text(
                    text = "Konu, Karakterler veya Durum",
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 6.dp)
                )
                OutlinedTextField(
                    value = customTopic,
                    onValueChange = { customTopic = it },
                    placeholder = { Text("Örn: Temel astronot olursa, akıllı buzdolabı...") },
                    shape = RoundedCornerShape(16.dp),
                    colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = AmberPrimary),
                    singleLine = false,
                    minLines = 2,
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("ai_topic_input")
                )

                // Quick Inspiration Chips
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState())
                        .padding(top = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    quickInspirations.forEach { inspiration ->
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                            modifier = Modifier.clickable { customTopic = inspiration }
                        ) {
                            Text(
                                text = "💡 $inspiration",
                                fontSize = 11.sp,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            }
        }

        // Generate Action Button
        item {
            Button(
                onClick = {
                    viewModel.generateAiJoke(customTopic, selectedCategory)
                },
                enabled = !isGenerating,
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (useHighThinking) Color(0xFF6200EA) else AmberPrimary
                ),
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp)
                    .testTag("generate_ai_joke_btn")
            ) {
                if (isGenerating) {
                    CircularProgressIndicator(
                        color = Color.White,
                        modifier = Modifier.size(20.dp),
                        strokeWidth = 2.dp
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text("Mizah Yazılıyor...", fontWeight = FontWeight.Bold)
                } else {
                    Icon(
                        imageVector = Icons.Default.AutoAwesome,
                        contentDescription = null,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = if (useHighThinking) "Fıkra Ustası ile Üret (Gemini 4 Argon)" else "Hızlı Fıkra Üret (Gemini 3.8)",
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.labelLarge
                    )
                }
            }

            if (!aiStatusMessage.isNullOrBlank()) {
                Text(
                    text = aiStatusMessage ?: "",
                    style = MaterialTheme.typography.bodySmall,
                    color = AmberPrimary,
                    modifier = Modifier.padding(top = 6.dp)
                )
            }
        }

        // Result Card if generated
        if (generatedJoke != null) {
            val joke = generatedJoke!!
            item {
                Card(
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surface
                    ),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("generated_joke_card")
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Surface(
                                color = CoralAccent.copy(alpha = 0.15f),
                                shape = RoundedCornerShape(8.dp)
                            ) {
                                Text(
                                    text = "✨ Yeni Üretildi • ${joke.category}",
                                    color = CoralAccent,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 11.sp,
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                )
                            }

                            Surface(
                                color = if (joke.modelUsed.contains("argon")) Color(0xFF6200EA).copy(alpha = 0.15f) else AmberPrimary.copy(alpha = 0.15f),
                                shape = RoundedCornerShape(8.dp)
                            ) {
                                Text(
                                    text = if (joke.modelUsed.contains("argon")) "Gemini 4 Argon" else "Gemini 3.8 Flash",
                                    color = if (joke.modelUsed.contains("argon")) Color(0xFF6200EA) else AmberPrimary,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 11.sp,
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        Text(
                            text = joke.title,
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = joke.content,
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onSurface,
                            lineHeight = 24.sp
                        )

                        // Thinking Analysis if present
                        if (!joke.thinkingAnalysis.isNullOrBlank()) {
                            Spacer(modifier = Modifier.height(10.dp))
                            OutlinedCard(
                                shape = RoundedCornerShape(12.dp),
                                colors = CardDefaults.outlinedCardColors(
                                    containerColor = Color(0xFF6200EA).copy(alpha = 0.05f)
                                ),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Column(modifier = Modifier.padding(10.dp)) {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Icon(
                                            imageVector = Icons.Default.Lightbulb,
                                            contentDescription = null,
                                            tint = Color(0xFF6200EA),
                                            modifier = Modifier.size(16.dp)
                                        )
                                        Spacer(modifier = Modifier.width(6.dp))
                                        Text(
                                            text = "Gemini 4 Argon Mizah Analizi",
                                            fontWeight = FontWeight.Bold,
                                            color = Color(0xFF6200EA),
                                            style = MaterialTheme.typography.labelSmall
                                        )
                                    }
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(
                                        text = joke.thinkingAnalysis,
                                        style = MaterialTheme.typography.bodySmall,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(14.dp))

                        // Action Buttons: Listen (TTS), Play Jingle (Lyria), Share to Community, Share Outside
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            // Sesli Dinle Button
                            val isSpeaking = playbackState.isSpeaking && playbackState.currentJokeTitle == joke.title
                            FilledTonalButton(
                                onClick = {
                                    if (isSpeaking) {
                                        viewModel.stopAudio()
                                    } else {
                                        viewModel.audioPlayer.playJoke(9999L, joke.title, joke.content)
                                    }
                                },
                                shape = RoundedCornerShape(12.dp),
                                modifier = Modifier.weight(1f)
                            ) {
                                Icon(
                                    imageVector = if (isSpeaking) Icons.Default.Stop else Icons.Default.PlayArrow,
                                    contentDescription = null,
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(if (isSpeaking) "Durdur" else "Seslendir", fontSize = 12.sp)
                            }

                            // Comedy Fanfare Jingle (Lyria-3)
                            FilledTonalButton(
                                onClick = {
                                    viewModel.playComedyJingleEffect()
                                },
                                shape = RoundedCornerShape(12.dp),
                                modifier = Modifier.weight(1f)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.MusicNote,
                                    contentDescription = null,
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("Jingle", fontSize = 12.sp)
                            }
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        // Publish to Community button
                        Button(
                            onClick = {
                                viewModel.publishGeneratedJokeToCommunity()
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = AmberPrimary),
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("publish_ai_joke_btn")
                        ) {
                            Icon(
                                imageVector = Icons.Default.Publish,
                                contentDescription = null,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "Toplulukta Paylaş & Yayınla",
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
        }
    }
}
