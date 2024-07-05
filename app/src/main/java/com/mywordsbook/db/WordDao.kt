package com.mywordsbook.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow


@Dao
interface WordDao {

    @Upsert()
    suspend fun addWord(word: Word)

    @Insert
    fun insertAll(users: List<Word>)


    @Query("SELECT * FROM Word")
    fun fetchAllTasks(): Flow<List<Word>>

    @Query("DELETE FROM word WHERE id = :id")
    fun deleteWordId(id: Int)
//
//    @Query("SELECT * FROM Word WHERE id=:id")
//    fun getWordWithId(id: Int): Flow<Word>
//
//    @Update()
//    suspend fun updateWord(word: Word)
}