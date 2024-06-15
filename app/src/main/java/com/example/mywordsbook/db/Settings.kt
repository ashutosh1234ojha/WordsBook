package com.example.mywordsbook.db

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Settings(
    var isCardView: Boolean=false,
    @PrimaryKey(true) var id: Int = 0,
)