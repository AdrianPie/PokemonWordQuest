package com.example.pokedextest.presentation.pokemonfightscreen

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokedextest.R
import com.example.pokedextest.data.models.Player
import com.example.pokedextest.data.models.Word
import com.example.pokedextest.data.remote.responses.Pokemon
import com.example.pokedextest.data.room.playerdb.PlayerRepository
import com.example.pokedextest.data.room.wordsdb.WordsRepository
import com.example.pokedextest.repository.PokemonRepository
import com.example.pokedextest.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.Locale
import javax.inject.Inject
import kotlin.random.Random
import kotlin.random.nextInt

@HiltViewModel
class PokemonFightViewModel @Inject constructor(
    private val repository: PokemonRepository,
    private val playerRepository: PlayerRepository,
    private val wordsRepository: WordsRepository): ViewModel() {

    private val _randomPokemonList = MutableStateFlow<List<Pokemon>>(listOf())
    val randomPokemonList: StateFlow<List<Pokemon>> get() = _randomPokemonList.asStateFlow()

    private val _allWordsList = MutableStateFlow<List<Word>>(listOf())
    val allWordsList: StateFlow<List<Word>> get() = _allWordsList.asStateFlow()

    private val _allWordsIdList = MutableStateFlow<List<Int>>(listOf())
    val allWordsIdList: StateFlow<List<Int>> get() = _allWordsIdList.asStateFlow()

    private val _randomWord = MutableStateFlow(Word())
    val randomWord: StateFlow<Word> get() = _randomWord.asStateFlow()

    private val _loadError = MutableStateFlow("")
    val loadError: StateFlow<String> get() = _loadError.asStateFlow()

    private val _hints = MutableStateFlow<List<Int>>(emptyList())
    val hints: StateFlow<List<Int>> get() = _hints.asStateFlow()

    private val _snackbarMessage = MutableStateFlow<String?>(null)
    val snackbarMessage: StateFlow<String?> = _snackbarMessage

    private val _rightWord = MutableStateFlow(Word())
    val rightWord: StateFlow<Word> get() = _rightWord.asStateFlow()

    private val _threeWords = MutableStateFlow<List<Word>>(listOf())
    val threeWords: StateFlow<List<Word>> get() = _threeWords.asStateFlow()

    private val _pokemonHp = MutableStateFlow<Double>(0.0)
    val pokemonHp: StateFlow<Double> get() = _pokemonHp.asStateFlow()

    private val _playerHp = MutableStateFlow<Double>(0.0)
    val playerHp: StateFlow<Double> get() = _playerHp.asStateFlow()

    private val _pokemonOffsetDpY = MutableStateFlow(80)
    val pokemonOffsetDpY = _pokemonOffsetDpY.asStateFlow()

    private val _pokemonOffsetDpX = MutableStateFlow(0)
    val pokemonOffsetDpX = _pokemonOffsetDpX.asStateFlow()

    private val _playerOffsetDpX = MutableStateFlow<Dp>(490.dp)
    val playerOffsetDpX: StateFlow<Dp> get() = _playerOffsetDpX.asStateFlow()

    private val _animateDmg = MutableStateFlow<Boolean>(true)
    val animateDmg: StateFlow<Boolean> get() = _animateDmg.asStateFlow()

    private val _questionNumber = MutableStateFlow(0)
    val questionNumber = _questionNumber.asStateFlow()

    private val _correctQuestionNumber = MutableStateFlow(0)
    val correctQuestionNumber = _correctQuestionNumber.asStateFlow()

    private val _expGained = MutableStateFlow(0)
    val expGained = _expGained.asStateFlow()

    private val _goldGained = MutableStateFlow(0)
    val goldGained = _goldGained.asStateFlow()

    private val _pokemonDefeated = MutableStateFlow(0)
    val pokemonDefeated = _pokemonDefeated.asStateFlow()

    var isDialogShown by mutableStateOf(false)
        private set

    private val _player: MutableStateFlow<Player> = MutableStateFlow(
        Player(
            avatar = R.drawable.player,
            vit = 1
    ))
    val player: StateFlow<Player>  get() = _player.asStateFlow()



init {
    getRandomPokemons(150)
    getAllWords()
    getAllWordsId()
    generateThreeRandomWords()
    getPlayer()
}



    fun updatePokemonHp(newHp: Double) {
        _pokemonHp.value = newHp
    }
    fun updatePlayerHp(newHp: Double) {
        _playerHp.value = newHp
    }
    fun updateDmgAnimation(anim: Boolean) {
        _animateDmg.value = anim
    }
    fun updatePlayer(exp: Int, gold: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val player = _player.value
            player.exp += exp
            player.gold += gold
            playerRepository.update(player)
        }
    }

    private fun getPlayer(){
        viewModelScope.launch(Dispatchers.IO) {
            val player = playerRepository.get(1)
            if (player != null){
                _player.value = player
            }
        }
    }

    fun getAllWords(){
        viewModelScope.launch(Dispatchers.IO) {
           val list =  wordsRepository.getAllWords()
            _allWordsList.value = list
        }
    }
    fun getAllWordsId(){
        viewModelScope.launch(Dispatchers.IO) {
            val list = wordsRepository.getAllWordIds()
            _allWordsIdList.value = list
        }
    }
    
    fun generateThreeRandomWords(){
        val words = ArrayList<Word>()
        viewModelScope.launch {
            delay(50)
            val allWordIds = wordsRepository.getAllWordIds()
            val threeRandomNumbers = allWordIds.shuffled().take(3)
            val firstNumber = threeRandomNumbers.elementAt(0)
            val secondNumber = threeRandomNumbers.elementAt(1)
            val thirdNumber = threeRandomNumbers.elementAt(2)
            val firstWord = wordsRepository.get(firstNumber)
            val secondWord = wordsRepository.get(secondNumber)
            val thirdWord = wordsRepository.get(thirdNumber)
            when(Random.nextInt(1..3)){
                1 -> _rightWord.value = firstWord
                2 -> _rightWord.value = secondWord
                3 -> _rightWord.value = thirdWord
            }
            words.add(firstWord)
            words.add(secondWord)
            words.add(thirdWord)
            _threeWords.value = words
        }
    }
    fun getThreeAnswerList(): List<String> {
        val words = _threeWords.value
        return listOf(words[0].english, words[1].english, words[2].english)
    }
    fun cleanHint() {
        viewModelScope.launch {
            _hints.value = emptyList()
        }
    }
    fun addWord(word: Word) {
        viewModelScope.launch {
            wordsRepository.insert(word)
        }
    }
    fun showHint(): String{
        var word = randomWord.value.polish
        var hint = ""
        if (word.isNotEmpty()){
            var i = 0
            var random = Random.nextInt(1,word.length)
            while (hints.value.contains(random)){
                random = Random.nextInt(1,word.length)
                i++
                if (i >50) {
                    break
                }
            }
            _hints.value += random
            _hints.value += 0
            for ((index,char) in word.toCharArray().withIndex()) {
                if (hints.value.contains(index)){
                    hint += char
                } else{
                    hint += "*"
                }
            }
        }
        return hint
    }
    fun roundTo1DecimalPlace(value: Double): Double {
        val decimalFormat = DecimalFormat("#.#", DecimalFormatSymbols(Locale.CANADA))
        return decimalFormat.format(value).toDouble()
    }
    fun getRandomWord(){
        viewModelScope.launch {

            val allWordIds = wordsRepository.getAllWordIds()
            val randomNumber = allWordIds[Random.nextInt(0,allWordIds.size -1)]
            val word = wordsRepository.get(randomNumber)
            _randomWord.value = word
        }
    }
    fun addPlayer(player:Player) {
        viewModelScope.launch {
            playerRepository.insert(player)
        }
    }
    fun showToast(text: String, context: Context) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
    }
    private fun getRandomPokemons(pokemonNumber: Int){
        val numbers = mutableSetOf<Int>()

        while (numbers.size < pokemonNumber) {
            val randomNumber = Random.nextInt(1200) + 1
            numbers.add(randomNumber)
        }
        viewModelScope.launch {
            for(i in numbers) {
                val result = repository.getPokemonInfo(i.toString())
                when(result) {
                    is Resource.Error -> _loadError.value = result.message!!
                    is Resource.Loading -> Log.d("dupsko", "loadPokemonPaginated: dupa")
                    is Resource.Success -> {
                        val pokemon = result.data
                        _randomPokemonList.value = randomPokemonList.value + pokemon!!
                        Log.d("dupsko", "getRandomPokemons: + ${_randomPokemonList.value.size}")
                    }
                }
            }
        }
    }
    fun pokemonDefeated(){
        _goldGained.value += 15
        _expGained.value += 10
        _pokemonDefeated.value += 1
        Log.d("mamcia", "wrongAnswer: g${_goldGained.value},e${_expGained.value}  ")
    }
    fun correctAnswer(){
        _correctQuestionNumber.value += 1
        _questionNumber.value += 1
        Log.d("mamcia", "wrongAnswer: q:${_questionNumber.value} , c: ${_correctQuestionNumber.value}")

    }
    fun wrongAnswer(){
        _questionNumber.value += 1
        Log.d("mamcia", "wrongAnswer: ${_questionNumber.value} ")
    }
    fun playerDefeated(){


    }
    fun onRetreatClick(){
        viewModelScope.launch {
            isDialogShown = true
        }
    }
    fun onDismissDialog(){
        isDialogShown = false
    }

}