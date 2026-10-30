package com.example.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.data.model.JokeEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface JokeDao {
    @Query("SELECT * FROM jokes ORDER BY createdAt DESC")
    fun getAllJokes(): Flow<List<JokeEntity>>

    @Query("SELECT * FROM jokes WHERE category = :category ORDER BY createdAt DESC")
    fun getJokesByCategory(category: String): Flow<List<JokeEntity>>

    @Query("SELECT * FROM jokes WHERE isFavorite = 1 ORDER BY createdAt DESC")
    fun getFavoriteJokes(): Flow<List<JokeEntity>>

    @Query("SELECT * FROM jokes WHERE title LIKE '%' || :query || '%' OR content LIKE '%' || :query || '%' ORDER BY createdAt DESC")
    fun searchJokes(query: String): Flow<List<JokeEntity>>

    @Query("SELECT * FROM jokes WHERE id = :id LIMIT 1")
    suspend fun getJokeById(id: Long): JokeEntity?

    @Query("SELECT COUNT(*) FROM jokes")
    suspend fun getJokeCount(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertJoke(joke: JokeEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(jokes: List<JokeEntity>)

    @Update
    suspend fun updateJoke(joke: JokeEntity)

    @Query("UPDATE jokes SET isFavorite = :isFavorite WHERE id = :id")
    suspend fun updateFavorite(id: Long, isFavorite: Boolean)

    @Query("UPDATE jokes SET likesCount = :newLikes, isLikedByUser = :isLiked WHERE id = :id")
    suspend fun updateLike(id: Long, newLikes: Int, isLiked: Boolean)

    @Query("UPDATE jokes SET commentsCount = commentsCount + 1, commentsJson = :commentsJson WHERE id = :id")
    suspend fun addComment(id: Long, commentsJson: String)

    @Delete
    suspend fun deleteJoke(joke: JokeEntity)
}
