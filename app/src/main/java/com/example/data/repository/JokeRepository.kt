package com.example.data.repository

import com.example.data.local.JokeDao
import com.example.data.model.CommentItem
import com.example.data.model.JokeEntity
import com.example.data.seed.SeedJokes
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONObject

class JokeRepository(private val jokeDao: JokeDao) {

    val allJokes: Flow<List<JokeEntity>> = jokeDao.getAllJokes()
    val favoriteJokes: Flow<List<JokeEntity>> = jokeDao.getFavoriteJokes()

    fun getJokesByCategory(category: String): Flow<List<JokeEntity>> {
        return if (category == "Tümü") {
            jokeDao.getAllJokes()
        } else {
            jokeDao.getJokesByCategory(category)
        }
    }

    fun searchJokes(query: String): Flow<List<JokeEntity>> {
        return jokeDao.searchJokes(query)
    }

    suspend fun ensureSeeded() = withContext(Dispatchers.IO) {
        val count = jokeDao.getJokeCount()
        if (count == 0) {
            jokeDao.insertAll(SeedJokes.initialJokes)
        }
    }

    suspend fun insertJoke(joke: JokeEntity): Long = withContext(Dispatchers.IO) {
        jokeDao.insertJoke(joke)
    }

    suspend fun toggleFavorite(joke: JokeEntity) = withContext(Dispatchers.IO) {
        jokeDao.updateFavorite(joke.id, !joke.isFavorite)
    }

    suspend fun toggleLike(joke: JokeEntity) = withContext(Dispatchers.IO) {
        val newLiked = !joke.isLikedByUser
        val newCount = if (newLiked) joke.likesCount + 1 else (joke.likesCount - 1).coerceAtLeast(0)
        jokeDao.updateLike(joke.id, newCount, newLiked)
    }

    suspend fun addComment(jokeId: Long, currentCommentsJson: String, author: String, text: String): List<CommentItem> = withContext(Dispatchers.IO) {
        val commentsList = parseComments(currentCommentsJson).toMutableList()
        val newComment = CommentItem(
            id = System.currentTimeMillis().toString(),
            author = author.ifBlank { "Mizah Dostu" },
            text = text.trim(),
            timestamp = System.currentTimeMillis()
        )
        commentsList.add(newComment)

        val jsonArray = JSONArray()
        commentsList.forEach { comment ->
            val obj = JSONObject()
            obj.put("id", comment.id)
            obj.put("author", comment.author)
            obj.put("text", comment.text)
            obj.put("timestamp", comment.timestamp)
            jsonArray.put(obj)
        }
        val updatedJson = jsonArray.toString()
        jokeDao.addComment(jokeId, updatedJson)
        commentsList
    }

    fun parseComments(json: String): List<CommentItem> {
        val list = mutableListOf<CommentItem>()
        try {
            val array = JSONArray(json)
            for (i in 0 until array.length()) {
                val obj = array.getJSONObject(i)
                list.add(
                    CommentItem(
                        id = obj.optString("id", i.toString()),
                        author = obj.optString("author", "Anonim"),
                        text = obj.optString("text", ""),
                        timestamp = obj.optLong("timestamp", System.currentTimeMillis())
                    )
                )
            }
        } catch (e: Exception) {
            // Ignore parse errors
        }
        return list
    }
}
