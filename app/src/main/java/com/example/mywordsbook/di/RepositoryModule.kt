package com.example.mywordsbook.di

import android.content.Context
import androidx.room.Room
import com.example.mywordsbook.db.WordDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object RepositoryModule {
    @Singleton
    @Provides
    fun getWordDatabase(@ApplicationContext context: Context): WordDatabase {
        return Room.databaseBuilder(
            context,
            WordDatabase::class.java,
            "words_database"
        ).fallbackToDestructiveMigration()
            .allowMainThreadQueries()
            .build()
    }
}
