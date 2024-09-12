package com.example.pokedextest.pokemonwordscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokedextest.data.models.Word
import com.example.pokedextest.data.room.wordsdb.WordsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonWordsScreenViewModel @Inject constructor(
    private val repository: WordsRepository
): ViewModel() {
    private val _wordsMapped = MutableStateFlow<Map<String, List<Word>>>(mapOf())
    val wordsMapped: StateFlow<Map<String, List<Word>>> get() = _wordsMapped.asStateFlow()

    init {
        createSortedListOfWords()
    }

    fun insertWord(polish: String, english: String, spanish: String, diff: Int) {
        val word = Word(polish = polish, english = english, spanish = spanish, difficulty = diff)
        viewModelScope.launch(Dispatchers.IO) {
            repository.insert(word)
        }
    }
    fun deleteWord(word: Word){
        viewModelScope.launch(Dispatchers.IO){
            repository.delete(word)
        }
    }
     fun createSortedListOfWords(){
        viewModelScope.launch(Dispatchers.IO) {
            delay(100)
            val wordList = repository.getAllWords()
            val mappedWords: Map<String, List<Word>> = wordList.groupBy {  it.polish.first().uppercaseChar().toString() }.toSortedMap()

            _wordsMapped.value = mappedWords
        }
    }
}