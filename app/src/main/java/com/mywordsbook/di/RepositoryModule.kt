package com.mywordsbook.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.mywordsbook.db.Word
import com.mywordsbook.db.WordDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.concurrent.Executors
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object RepositoryModule {
    @Singleton
    @Provides
    fun getWordDatabase(@ApplicationContext context: Context): WordDatabase {

        var wordDatabase: WordDatabase? = null

        return wordDatabase ?: synchronized(this) {
            val instance = Room.databaseBuilder(
                context.applicationContext,
                WordDatabase::class.java,
                "app_database"
            )
                .addCallback(object : RoomDatabase.Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)

                        val words = listOf(
                            Word(
                                wording = "One",
                                meaning = "1",
                                createdDateTime = SimpleDateFormat(
                                    "dd/M/yyyy hh:mm:ss",
                                    Locale.ENGLISH
                                ).format(Date())
                            ),
                            Word(
                                wording = "Two",
                                meaning = "2",
                                createdDateTime = SimpleDateFormat(
                                    "dd/M/yyyy hh:mm:ss",
                                    Locale.ENGLISH
                                ).format(Date())
                            ),

                            Word(
                                wording = "Three",
                                meaning = "3",
                                createdDateTime = SimpleDateFormat(
                                    "dd/M/yyyy hh:mm:ss",
                                    Locale.ENGLISH
                                ).format(Date())
                            ),
                            Word(
                                wording = "Four",
                                meaning = "4",
                                createdDateTime = SimpleDateFormat(
                                    "dd/M/yyyy hh:mm:ss",
                                    Locale.ENGLISH
                                ).format(Date())
                            )

                        )

                        wordDatabase?.let {
                            Executors.newSingleThreadExecutor().execute {
                                it.wordDao()?.insertAll(words)
                            }
                        }
                    }
                })
                .build()
            wordDatabase = instance
            instance
        }

    }
}
