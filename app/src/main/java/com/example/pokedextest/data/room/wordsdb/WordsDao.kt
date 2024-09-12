package com.example.pokedextest.data.room.wordsdb

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.pokedextest.data.models.Word

@Dao
interface WordsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWord(word: Word)

    @Update
    suspend fun updateWord(word: Word)

    @Query("SELECT idWord FROM word_list")
    suspend fun getAllWordIds(): List<Int>

    @Delete
    suspend fun deleteWord(word: Word)

    @Query("SELECT * FROM word_list ORDER BY idWord DESC")
    suspend fun getAllWords(): MutableList<Word>

    @Query("SELECT * FROM word_list WHERE idWord like :id")
    suspend fun getWord(id : Int) : Word
}