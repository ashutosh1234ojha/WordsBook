package com.example.mywordsbook

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mywordsbook.db.Word
import com.example.mywordsbook.db.WordDao
import com.example.mywordsbook.db.WordDatabase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.transform

import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Collections
import java.util.Date
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class CommonViewModel @Inject constructor(wordDatabase: WordDatabase) : ViewModel() {
    var dao: WordDao?

    var selectedWord: Word? = null

//    lateinit var flowWordList: Flow<List<Word>>

    //    var flowWordList by mutableStateOf(emptyList<Word>())
    var list = MutableStateFlow<List<Word>>(emptyList())
    var quizWordOptions = MutableStateFlow<List<Word>>(emptyList())

    init {
        dao = wordDatabase.wordDao()
        getSavedWords(false)
        getQuizOptions()

    }

    fun getSavedWords(isShuffled: Boolean) {
        viewModelScope.launch {
            if (isShuffled) {
                dao?.fetchAllTasks()?.shuffleFlow()?.collect {
                    list.value = it
                }
            } else {
                dao?.fetchAllTasks()?.collect {
                    list.value = it
                }
            }

        }
    }

    fun getSavedWordsLatestFirst(isDescending: Boolean) {
        viewModelScope.launch {
            dao?.fetchAllTasks()?.collect {
//                val temp = it
//                val sorted = arrayListOf<Word>()
                if (isDescending) {
                    list.value = it.sortedByDescending { it.createdDateTime }

                } else {
                    list.value = it.sortedByDescending { it.createdDateTime }.reversed()

                }


            }

        }
    }


    fun _setSelectedWord(word: Word?) {
        selectedWord = word
    }

    fun saveWordMeaning(_word: String, _meaning: String,_isImportant:Boolean=false) {
        viewModelScope.launch(Dispatchers.IO) {
            if (selectedWord == null) {
                dao?.addWord(
                    Word(
                        meaning = _meaning,
                        wording = _word,
                        createdDateTime = SimpleDateFormat(
                            "dd/M/yyyy hh:mm:ss",
                            Locale.ENGLISH
                        ).format(
                            Date()
                        ), isImportant = _isImportant
                    )
                )
            } else {
                selectedWord?.apply {
                    meaning = _meaning
                    wording = _word
                    isImportant=_isImportant
                    dao?.addWord(this)
                }
            }

        }
    }

    fun deleteWord() {
        viewModelScope.launch(Dispatchers.IO) {
            dao?.deleteWordId(selectedWord!!.id)
        }


    }

    private fun Flow<List<Word>>.shuffleFlow(): Flow<List<Word>> {
        return this.transform { originalList ->
            if (originalList.isNotEmpty()) {
                val shuffledList = originalList.toMutableList()
                Collections.shuffle(shuffledList)
                emit(shuffledList)
            } else {
                emit(originalList)
            }
        }
    }

    fun getQuizOptions() {
        viewModelScope.launch {
            dao?.fetchAllTasks()?.shuffleFlow()?.collect {
                if (it.size >= 4)
                    quizWordOptions.value = it.subList(0, 4)
            }


        }
    }

}