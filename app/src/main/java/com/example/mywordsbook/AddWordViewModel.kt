package com.example.mywordsbook

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mywordsbook.db.Word
import com.example.mywordsbook.db.WordDao
import com.example.mywordsbook.db.WordDatabase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddWordViewModel @Inject constructor(wordDatabase: WordDatabase) :
    ViewModel() {
    var dao: WordDao?

    init {
        dao = wordDatabase.wordDao()
    }

    fun saveWordMeaning(word: String, meaning: String) {
        viewModelScope.launch(Dispatchers.IO) {
            dao?.addWord(Word(meaning = meaning, wording = word))

        }
    }
}
