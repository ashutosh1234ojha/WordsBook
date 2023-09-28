package com.example.mywordsbook.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Word(@PrimaryKey(true) val id: Int=0, val wording: String, val meaning: String)