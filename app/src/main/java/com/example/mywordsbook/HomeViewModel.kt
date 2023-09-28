package com.example.mywordsbook

import androidx.lifecycle.ViewModel
import com.example.mywordsbook.db.Word
import com.example.mywordsbook.db.WordDao
import com.example.mywordsbook.db.WordDatabase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
 class HomeViewModel @Inject constructor(wordDatabase: WordDatabase) :
    ViewModel() {
    var dao: WordDao?

    init {
        dao = wordDatabase.wordDao()
    }

    fun getSavedWords(): Flow<List<Word>> {
        return dao?.fetchAllTasks()!!
    }
}