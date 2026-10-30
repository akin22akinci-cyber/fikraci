package com.example.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.data.model.JokeEntity
import com.example.data.seed.SeedJokes
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = [JokeEntity::class], version = 1, exportSchema = false)
abstract class JokeDatabase : RoomDatabase() {
    abstract fun jokeDao(): JokeDao

    companion object {
        @Volatile
        private var INSTANCE: JokeDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): JokeDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    JokeDatabase::class.java,
                    "fikraci_database"
                )
                    .addCallback(JokeDatabaseCallback(scope))
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }

    private class JokeDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch(Dispatchers.IO) {
                    populateDatabase(database.jokeDao())
                }
            }
        }

        suspend fun populateDatabase(jokeDao: JokeDao) {
            jokeDao.insertAll(SeedJokes.initialJokes)
        }
    }
}
