package com.example.pokedextest.data.room.wordsdb

import com.example.pokedextest.data.models.Word
import javax.inject.Inject


class WordsRepository @Inject constructor(
    private val wordsDao: WordsDao
    ) {
  //  val allWords: MutableList<Word> = wordsDao.getAllWords()

    suspend fun insert(word: Word) {
        wordsDao.insertWord(word)
    }

    suspend fun update(word: Word) {
        wordsDao.updateWord(word)
    }

    suspend fun delete(word: Word) {
        wordsDao.deleteWord(word)
    }
    suspend fun getAllWordIds(): List<Int> {
        return wordsDao.getAllWordIds()
    }
    suspend fun get(wordId: Int): Word{
        return wordsDao.getWord(wordId)
    }
    suspend fun getAllWords(): MutableList<Word>{
        return wordsDao.getAllWords()
    }
}