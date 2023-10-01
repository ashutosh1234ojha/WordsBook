package com.example.mywordsbook

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mywordsbook.db.Word
import com.example.mywordsbook.db.WordDao
import com.example.mywordsbook.db.WordDatabase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CommonViewModel @Inject constructor(wordDatabase: WordDatabase) :
    ViewModel() {
    var dao: WordDao?

    var selectedWord: Word? = null

    init {
        dao = wordDatabase.wordDao()
    }

    fun getSavedWords(): Flow<List<Word>> {
        return dao?.fetchAllTasks()!!
    }

    fun _setSelectedWord(word: Word) {
        selectedWord = word
    }

    fun saveWordMeaning(_word: String, _meaning: String) {
        viewModelScope.launch(Dispatchers.IO) {
            if (selectedWord == null) {
                dao?.addWord(Word(meaning = _meaning, wording = _word))
            } else {
                selectedWord?.apply {
                    meaning = _meaning
                    wording = _word
                    dao?.addWord(this)
                }


            }

        }
    }
}