package com.example.mywordsbook.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Word(val wording: String, val meaning: String, @PrimaryKey(true) val id: Int = 0)