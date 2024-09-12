package com.example.pokedextest.data.room.wordsdb

import android.content.Context
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

import com.example.pokedextest.data.models.Player
import com.example.pokedextest.data.models.Word


@Database(entities = [Word::class], version = 1, exportSchema = false)
abstract class WordsDatabase : RoomDatabase() {

    abstract fun wordsDao(): WordsDao

}