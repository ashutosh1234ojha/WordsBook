package com.mywordsbook

import android.os.Build
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.*
import androidx.core.os.BuildCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.BuildConfig
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.mywordsbook.db.SettingDao
import com.mywordsbook.db.Settings
import com.mywordsbook.db.Word
import com.mywordsbook.db.WordDao
import com.mywordsbook.db.WordDatabase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
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
    private var dao: WordDao? = wordDatabase.wordDao()

    var selectedWord: Word? = null
    var list = MutableStateFlow<List<Word>>(emptyList())
    var quizWordOptions = MutableStateFlow<List<Word>>(emptyList())
    var score = MutableStateFlow<Int>(0)
    var settingDao: SettingDao? = wordDatabase.settingDao()
    private val _isSwitchOn = MutableStateFlow(false)
    val isSwitchOn: StateFlow<Boolean> get() = _isSwitchOn

    private val _isDarkTheme = MutableStateFlow(false)
    val isDarkTheme: StateFlow<Boolean> get() = _isDarkTheme

    private var setting: Settings? = null
    val db = Firebase.firestore

    private val _currentVersion = MutableStateFlow(1)
    val currentVersion: StateFlow<Int> get() = _currentVersion


    init {
        viewModelScope.launch {
            settingDao?.getSetting()?.collect {
                setting = it
                Log.d("Setting", "Setting carView ${it.hashCode()}")
                Log.d("Setting", "Setting carView ${it?.isCardView}")
                Log.d("Setting", "Setting dark ${it?.isDarkTheme}")
                _isSwitchOn.value = it?.isCardView ?: false
                _isDarkTheme.value = it?.isDarkTheme ?: false
            }

        }

        getSavedWords(false)
        getQuizOptions()
        //addCurrentVersion()
        getCurrentVersion()

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

    fun saveWordMeaning(_word: String, _meaning: String, _isImportant: Boolean = false) {
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
                    isImportant = _isImportant
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
                shuffledList.shuffle()
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


    fun updateScore(isCorrect: Boolean) {
        if (isCorrect) {
            score.value += 1
        } else if (score.value != 0) {
            score.value -= 1
        }
    }

    fun updateListView(isCardView: Boolean) {
        viewModelScope.launch {
            Log.d("Setting", "Card view update $isCardView}")

            if (setting == null) {

                settingDao?.updateSetting(Settings(isCardView = isCardView, id = 1))
                _isSwitchOn.value = isCardView
            } else {
                setting?.isCardView = isCardView
                settingDao?.updateSetting(setting!!)
                _isSwitchOn.value = isCardView
            }

        }
    }

    fun updateTheme(isDarkTheme: Boolean) {
        viewModelScope.launch {
            Log.d("Setting", "dark update $isDarkTheme")

            if (setting == null) {
                settingDao?.updateSetting(Settings(isDarkTheme = isDarkTheme, id = 1))
                _isDarkTheme.value = isDarkTheme
            } else {
                setting?.isDarkTheme = isDarkTheme
                settingDao?.updateSetting(setting!!)
                _isDarkTheme.value = isDarkTheme
            }

        }
    }

    private fun getCurrentVersion() {
        db.collection("Version")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    Log.d("TAG", "${document.id} => ${document.data}")
                    _currentVersion.value = (document.data["versionCode"].toString()).toInt()
                }
            }
            .addOnFailureListener { exception ->
                Log.w("TAG", "Error getting documents.", exception)
            }
    }


}