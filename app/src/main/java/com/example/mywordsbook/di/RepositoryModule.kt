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

//    @Singleton
//    @Provides
//    fun getWordDatabase(@ApplicationContext appContext: Context): WordDatabase {
//        return WordDatabase.getInstance(appContext)
//
//    }


    @Singleton
    @Provides
    fun getWordDatabase(@ApplicationContext context: Context): WordDatabase {//        synchronized(this) {
//            var instance = WordDatabase.INSTANCE
//
//            if (instance == null) {
        return Room.databaseBuilder(
            context,
            WordDatabase::class.java,
            "product_database"
        ).fallbackToDestructiveMigration()
//            .allowMainThreadQueries()
            .build()

//                WordDatabase.INSTANCE = instance
//            }
    }
}
