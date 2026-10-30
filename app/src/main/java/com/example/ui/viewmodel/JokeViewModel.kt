package com.example.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.local.JokeDatabase
import com.example.data.model.CommentItem
import com.example.data.model.JokeEntity
import com.example.data.repository.JokeRepository
import com.example.service.AudioPlaybackState
import com.example.service.FikraAudioPlayer
import com.example.service.GeminiService
import com.example.service.GeneratedJokeResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

enum class SortMode(val label: String) {
    POPULAR("En Çok Güldürenler"),
    NEWEST("En Yeniler"),
    RANDOM("Karışık")
}

class JokeViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: JokeRepository
    val audioPlayer: FikraAudioPlayer = FikraAudioPlayer(application)
    private val geminiService = GeminiService()

    // Navigation Tab (0: Keşfet, 1: AI Atölye, 2: Favoriler, 3: Topluluk)
    private val _selectedTab = MutableStateFlow(0)
    val selectedTab: StateFlow<Int> = _selectedTab.asStateFlow()

    // Category & Search Filters
    private val _selectedCategory = MutableStateFlow("Tümü")
    val selectedCategory: StateFlow<String> = _selectedCategory.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _sortMode = MutableStateFlow(SortMode.POPULAR)
    val sortMode: StateFlow<SortMode> = _sortMode.asStateFlow()

    // UI Dialog & Sheet states
    private val _isNewJokeDialogOpen = MutableStateFlow(false)
    val isNewJokeDialogOpen: StateFlow<Boolean> = _isNewJokeDialogOpen.asStateFlow()

    private val _jokeToShare = MutableStateFlow<JokeEntity?>(null)
    val jokeToShare: StateFlow<JokeEntity?> = _jokeToShare.asStateFlow()

    private val _activeJokeForComments = MutableStateFlow<JokeEntity?>(null)
    val activeJokeForComments: StateFlow<JokeEntity?> = _activeJokeForComments.asStateFlow()

    private val _activeComments = MutableStateFlow<List<CommentItem>>(emptyList())
    val activeComments: StateFlow<List<CommentItem>> = _activeComments.asStateFlow()

    // AI Studio State
    private val _isGeneratingAi = MutableStateFlow(false)
    val isGeneratingAi: StateFlow<Boolean> = _isGeneratingAi.asStateFlow()

    private val _useHighThinking = MutableStateFlow(false)
    val useHighThinking: StateFlow<Boolean> = _useHighThinking.asStateFlow()

    private val _generatedJoke = MutableStateFlow<GeneratedJokeResult?>(null)
    val generatedJoke: StateFlow<GeneratedJokeResult?> = _generatedJoke.asStateFlow()

    private val _aiStatusMessage = MutableStateFlow<String?>(null)
    val aiStatusMessage: StateFlow<String?> = _aiStatusMessage.asStateFlow()

    // Surprise Joke Dialog State
    private val _surpriseJoke = MutableStateFlow<JokeEntity?>(null)
    val surpriseJoke: StateFlow<JokeEntity?> = _surpriseJoke.asStateFlow()

    val audioPlaybackState: StateFlow<AudioPlaybackState> = audioPlayer.playbackState

    val allCategories = listOf(
        "Tümü",
        "Nasreddin Hoca",
        "Temel ile Dursun",
        "Bektaşi",
        "Okul & Öğretmen",
        "Doktor & Hasta",
        "Modern Hayat",
        "Yapay Zekâ"
    )

    init {
        val database = JokeDatabase.getDatabase(application, viewModelScope)
        repository = JokeRepository(database.jokeDao())
        viewModelScope.launch {
            repository.ensureSeeded()
        }
    }

    // Filtered and Sorted Jokes Stream
    val jokes: StateFlow<List<JokeEntity>> = combine(
        repository.allJokes,
        _selectedCategory,
        _searchQuery,
        _sortMode
    ) { allList, category, query, sort ->
        var filtered = allList

        if (category != "Tümü") {
            filtered = filtered.filter { it.category == category }
        }

        if (query.isNotBlank()) {
            val q = query.trim().lowercase()
            filtered = filtered.filter {
                it.title.lowercase().contains(q) || it.content.lowercase().contains(q) || it.authorName.lowercase().contains(q)
            }
        }

        when (sort) {
            SortMode.POPULAR -> filtered.sortedByDescending { it.likesCount }
            SortMode.NEWEST -> filtered.sortedByDescending { it.createdAt }
            SortMode.RANDOM -> filtered.shuffled()
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val favoriteJokes: StateFlow<List<JokeEntity>> = repository.favoriteJokes
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun selectTab(index: Int) {
        _selectedTab.value = index
    }

    fun selectCategory(category: String) {
        _selectedCategory.value = category
    }

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun setSortMode(mode: SortMode) {
        _sortMode.value = mode
    }

    fun toggleFavorite(joke: JokeEntity) {
        viewModelScope.launch {
            repository.toggleFavorite(joke)
        }
    }

    fun toggleLike(joke: JokeEntity) {
        viewModelScope.launch {
            repository.toggleLike(joke)
            audioPlayer.playPunchlineRimshot()
        }
    }

    fun playJokeAudio(joke: JokeEntity) {
        audioPlayer.playJoke(joke.id, joke.title, joke.content)
    }

    fun stopAudio() {
        audioPlayer.stop()
    }

    fun playRimshotEffect() {
        audioPlayer.playPunchlineRimshot()
    }

    fun playComedyJingleEffect() {
        audioPlayer.playComedyJingle()
    }

    fun openShareDialog(joke: JokeEntity) {
        _jokeToShare.value = joke
    }

    fun closeShareDialog() {
        _jokeToShare.value = null
    }

    fun openNewJokeDialog() {
        _isNewJokeDialogOpen.value = true
    }

    fun closeNewJokeDialog() {
        _isNewJokeDialogOpen.value = false
    }

    fun openComments(joke: JokeEntity) {
        _activeJokeForComments.value = joke
        _activeComments.value = repository.parseComments(joke.commentsJson)
    }

    fun closeComments() {
        _activeJokeForComments.value = null
        _activeComments.value = emptyList()
    }

    fun addComment(jokeId: Long, author: String, text: String) {
        val currentJoke = _activeJokeForComments.value ?: return
        viewModelScope.launch {
            val updated = repository.addComment(jokeId, currentJoke.commentsJson, author, text)
            _activeComments.value = updated
        }
    }

    fun publishNewJoke(title: String, content: String, category: String, author: String) {
        viewModelScope.launch {
            val newJoke = JokeEntity(
                title = title.trim(),
                content = content.trim(),
                category = category,
                authorName = author.trim().ifBlank { "Anonim Fıkracı" },
                likesCount = 1,
                isLikedByUser = true,
                createdAt = System.currentTimeMillis()
            )
            repository.insertJoke(newJoke)
            closeNewJokeDialog()
            playRimshotEffect()
        }
    }

    fun toggleHighThinking(enable: Boolean) {
        _useHighThinking.value = enable
    }

    fun generateAiJoke(topic: String, category: String) {
        viewModelScope.launch {
            _isGeneratingAi.value = true
            _aiStatusMessage.value = if (_useHighThinking.value) {
                "Gemini 4 Argon (Derin Düşünce Modu) mizah kurgusu ve vurucu punchline hazırlıyor..."
            } else {
                "Gemini 3.8 Flash fıkrayı kaleme alıyor..."
            }

            try {
                val result = geminiService.generateJoke(
                    topicOrCharacter = topic,
                    category = category,
                    useHighThinking = _useHighThinking.value
                )
                _generatedJoke.value = result
                _aiStatusMessage.value = null
                playRimshotEffect()
            } catch (e: Exception) {
                _aiStatusMessage.value = "Bir hata oluştu: ${e.localizedMessage}"
            } finally {
                _isGeneratingAi.value = false
            }
        }
    }

    fun publishGeneratedJokeToCommunity() {
        val generated = _generatedJoke.value ?: return
        viewModelScope.launch {
            val jokeEntity = JokeEntity(
                title = generated.title,
                content = generated.content,
                category = generated.category,
                authorName = if (generated.modelUsed.contains("argon")) "Gemini 4 Argon (Usta)" else "Gemini 3.8 Flash",
                likesCount = 5,
                isLikedByUser = true,
                isAiGenerated = true,
                aiModelUsed = generated.modelUsed,
                thinkingAnalysis = generated.thinkingAnalysis,
                createdAt = System.currentTimeMillis()
            )
            repository.insertJoke(jokeEntity)
            _generatedJoke.value = null
            _selectedTab.value = 0 // Navigate to Explore tab to see it!
            playComedyJingleEffect()
        }
    }

    fun triggerSurpriseJoke() {
        val currentJokes = jokes.value
        if (currentJokes.isNotEmpty()) {
            _surpriseJoke.value = currentJokes.random()
            playRimshotEffect()
        }
    }

    fun closeSurpriseJoke() {
        _surpriseJoke.value = null
    }

    override fun onCleared() {
        super.onCleared()
        audioPlayer.release()
    }
}
