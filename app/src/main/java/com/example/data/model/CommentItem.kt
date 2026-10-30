package com.example.data.model

data class CommentItem(
    val id: String,
    val author: String,
    val text: String,
    val timestamp: Long = System.currentTimeMillis()
)
