package com.example.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "jokes")
data class JokeEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val title: String,
    val content: String,
    val category: String,
    val authorName: String = "Anonim Mizahçı",
    val likesCount: Int = 0,
    val isLikedByUser: Boolean = false,
    val isFavorite: Boolean = false,
    val commentsCount: Int = 0,
    val commentsJson: String = "[]",
    val createdAt: Long = System.currentTimeMillis(),
    val isAiGenerated: Boolean = false,
    val aiModelUsed: String? = null,
    val thinkingAnalysis: String? = null
)
