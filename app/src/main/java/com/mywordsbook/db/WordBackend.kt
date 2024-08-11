package com.mywordsbook.db

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey

data class WordBackend(
    var wording: String,
    var meaning: String,
    var id: Int = 0,
    var isImportant: Boolean = false,
    var createdDateTime: String,
)