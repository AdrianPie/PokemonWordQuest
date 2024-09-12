package com.example.pokedextest.di

import android.content.Context
import androidx.room.Room
import com.example.pokedextest.data.models.Player
import com.example.pokedextest.data.room.playerdb.PlayerDao
import com.example.pokedextest.data.room.playerdb.PlayerDatabase
import com.example.pokedextest.data.room.wordsdb.WordsDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DbModule {

    @Provides
    @Singleton
    fun providerDatabase(
        @ApplicationContext context : Context) =
        Room.databaseBuilder(
            context,
            PlayerDatabase::class.java,
            "playerList_db")
            .build()
    @Provides
    @Singleton
    fun provideWordsDatabase(
        @ApplicationContext context : Context) =
        Room.databaseBuilder(
            context,
            WordsDatabase::class.java,
            "wordsList_db")
            .createFromAsset("database/words.db")
            .build()

    @Provides
    @Singleton
    fun provideDao(db : PlayerDatabase) = db.playerDao()

    @Provides
    @Singleton
    fun provideWordDao(db: WordsDatabase) = db.wordsDao()

    @Provides
    fun provideEntity() = Player()

}