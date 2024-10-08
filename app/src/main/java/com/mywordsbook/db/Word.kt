package com.mywordsbook.db

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Word(
    var wording: String,
    var meaning: String,
    @PrimaryKey(true) var id: Int = 0,
    var isImportant: Boolean = false,
    var createdDateTime: String,
    var isSynced: Boolean = false
)