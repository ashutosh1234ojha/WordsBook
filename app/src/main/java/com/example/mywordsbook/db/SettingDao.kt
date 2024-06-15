package com.example.mywordsbook.db

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow


@Dao
interface SettingDao {
    @Upsert()
    suspend fun updateSetting(word: Settings)


    @Query("SELECT * FROM Settings")
    fun getSetting(): Flow<Settings?>



}