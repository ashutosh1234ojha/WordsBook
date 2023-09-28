package com.example.mywordsbook.db

import android.provider.ContactsContract.CommonDataKinds.Note
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow


@Dao
interface WordDao {

    @Insert
    fun addWord(word: Word)


    @Query("SELECT * FROM Word")
    fun fetchAllTasks(): Flow<List<Word>>
}