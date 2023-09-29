package com.example.mywordsbook.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow


@Dao
interface WordDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addWord(word: Word)


    @Query("SELECT * FROM Word")
    fun fetchAllTasks(): Flow<List<Word>>
}