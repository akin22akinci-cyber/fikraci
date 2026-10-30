package com.example.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Casino
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Stop
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.ui.components.LiveAudioWaveIndicator
import com.example.ui.dialogs.CommentsBottomSheet
import com.example.ui.dialogs.NewJokeDialog
import com.example.ui.dialogs.ShareJokeDialog
import com.example.ui.dialogs.SurpriseJokeDialog
import com.example.ui.screens.AiStudioScreen
import com.example.ui.screens.CommunityScreen
import com.example.ui.screens.FavoritesScreen
import com.example.ui.screens.HomeScreen
import com.example.ui.theme.AmberPrimary
import com.example.ui.theme.AmberSecondary
import com.example.ui.viewmodel.JokeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(viewModel: JokeViewModel) {
    val selectedTab by viewModel.selectedTab.collectAsState()
    val isNewJokeDialogOpen by viewModel.isNewJokeDialogOpen.collectAsState()
    val jokeToShare by viewModel.jokeToShare.collectAsState()
    val activeJokeForComments by viewModel.activeJokeForComments.collectAsState()
    val activeComments by viewModel.activeComments.collectAsState()
    val surpriseJoke by viewModel.surpriseJoke.collectAsState()
    val playbackState by viewModel.audioPlaybackState.collectAsState()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_fikra_logo),
                            contentDescription = "Logo",
                            modifier = Modifier
                                .size(34.dp)
                                .clip(RoundedCornerShape(8.dp))
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Fıkracı",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                },
                actions = {
                    // Quick Surprise Joke
                    IconButton(
                        onClick = { viewModel.triggerSurpriseJoke() },
                        modifier = Modifier.testTag("topbar_surprise_btn")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Casino,
                            contentDescription = "Şansıma Gül",
                            tint = AmberPrimary
                        )
                    }
                    // Quick Comedy Jingle
                    IconButton(
                        onClick = { viewModel.playComedyJingleEffect() },
                        modifier = Modifier.testTag("topbar_jingle_btn")
                    ) {
                        Icon(
                            imageVector = Icons.Default.MusicNote,
                            contentDescription = "Jingle",
                            tint = AmberSecondary
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            )
        },
        bottomBar = {
            Column(modifier = Modifier.navigationBarsPadding()) {
                // Floating Audio Playing Strip if TTS is speaking
                AnimatedVisibility(
                    visible = playbackState.isSpeaking,
                    enter = slideInVertically(initialOffsetY = { it }),
                    exit = slideOutVertically(targetOffsetY = { it })
                ) {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 4.dp),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = AmberPrimary),
                        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 14.dp, vertical = 8.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.weight(1f)
                            ) {
                                LiveAudioWaveIndicator()
                                Spacer(modifier = Modifier.width(10.dp))
                                Column {
                                    Text(
                                        text = "Sesli Fıkra Okunuyor...",
                                        style = MaterialTheme.typography.labelSmall,
                                        color = Color.White.copy(alpha = 0.8f)
                                    )
                                    Text(
                                        text = playbackState.currentJokeTitle ?: "",
                                        style = MaterialTheme.typography.bodyMedium,
                                        fontWeight = FontWeight.Bold,
                                        color = Color.White,
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis
                                    )
                                }
                            }

                            IconButton(
                                onClick = { viewModel.stopAudio() },
                                modifier = Modifier
                                    .size(36.dp)
                                    .clip(CircleShape)
                                    .background(Color.White.copy(alpha = 0.2f))
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Stop,
                                    contentDescription = "Durdur",
                                    tint = Color.White
                                )
                            }
                        }
                    }
                }

                NavigationBar(
                    containerColor = MaterialTheme.colorScheme.surface,
                    tonalElevation = 8.dp
                ) {
                    NavigationBarItem(
                        selected = selectedTab == 0,
                        onClick = { viewModel.selectTab(0) },
                        icon = { Icon(Icons.Default.Explore, contentDescription = "Keşfet") },
                        label = { Text("Keşfet", style = MaterialTheme.typography.labelSmall) },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = AmberPrimary,
                            selectedTextColor = AmberPrimary,
                            indicatorColor = AmberPrimary.copy(alpha = 0.15f)
                        ),
                        modifier = Modifier.testTag("tab_explore")
                    )

                    NavigationBarItem(
                        selected = selectedTab == 1,
                        onClick = { viewModel.selectTab(1) },
                        icon = { Icon(Icons.Default.AutoAwesome, contentDescription = "AI Atölye") },
                        label = { Text("AI Atölye", style = MaterialTheme.typography.labelSmall) },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = AmberPrimary,
                            selectedTextColor = AmberPrimary,
                            indicatorColor = AmberPrimary.copy(alpha = 0.15f)
                        ),
                        modifier = Modifier.testTag("tab_ai_studio")
                    )

                    NavigationBarItem(
                        selected = selectedTab == 2,
                        onClick = { viewModel.selectTab(2) },
                        icon = { Icon(Icons.Default.Star, contentDescription = "Favoriler") },
                        label = { Text("Favoriler", style = MaterialTheme.typography.labelSmall) },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = AmberPrimary,
                            selectedTextColor = AmberPrimary,
                            indicatorColor = AmberPrimary.copy(alpha = 0.15f)
                        ),
                        modifier = Modifier.testTag("tab_favorites")
                    )

                    NavigationBarItem(
                        selected = selectedTab == 3,
                        onClick = { viewModel.selectTab(3) },
                        icon = { Icon(Icons.Default.People, contentDescription = "Topluluk") },
                        label = { Text("Topluluk", style = MaterialTheme.typography.labelSmall) },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = AmberPrimary,
                            selectedTextColor = AmberPrimary,
                            indicatorColor = AmberPrimary.copy(alpha = 0.15f)
                        ),
                        modifier = Modifier.testTag("tab_community")
                    )
                }
            }
        },
        floatingActionButton = {
            if (selectedTab == 0 || selectedTab == 3) {
                FloatingActionButton(
                    onClick = { viewModel.openNewJokeDialog() },
                    containerColor = AmberPrimary,
                    contentColor = Color.White,
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier.testTag("fab_new_joke")
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 14.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(imageVector = Icons.Default.Add, contentDescription = "Fıkra Paylaş")
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "Fıkra Paylaş",
                            style = MaterialTheme.typography.labelLarge,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when (selectedTab) {
                0 -> HomeScreen(viewModel = viewModel)
                1 -> AiStudioScreen(viewModel = viewModel)
                2 -> FavoritesScreen(viewModel = viewModel)
                3 -> CommunityScreen(viewModel = viewModel)
            }
        }
    }

    // New Joke Dialog
    if (isNewJokeDialogOpen) {
        NewJokeDialog(
            categories = viewModel.allCategories,
            onDismiss = { viewModel.closeNewJokeDialog() },
            onPublish = { title, content, category, author ->
                viewModel.publishNewJoke(title, content, category, author)
            }
        )
    }

    // Share Dialog
    if (jokeToShare != null) {
        ShareJokeDialog(
            joke = jokeToShare!!,
            onDismiss = { viewModel.closeShareDialog() }
        )
    }

    // Comments Bottom Sheet
    if (activeJokeForComments != null) {
        CommentsBottomSheet(
            joke = activeJokeForComments!!,
            comments = activeComments,
            onDismiss = { viewModel.closeComments() },
            onAddComment = { author, text ->
                viewModel.addComment(activeJokeForComments!!.id, author, text)
            }
        )
    }

    // Surprise Joke Dialog
    if (surpriseJoke != null) {
        val isSpeakingSurprise = playbackState.isSpeaking && playbackState.currentJokeId == surpriseJoke!!.id
        SurpriseJokeDialog(
            joke = surpriseJoke!!,
            isSpeaking = isSpeakingSurprise,
            onPlayAudio = { viewModel.playJokeAudio(surpriseJoke!!) },
            onStopAudio = { viewModel.stopAudio() },
            onDismiss = { viewModel.closeSurpriseJoke() }
        )
    }
}
