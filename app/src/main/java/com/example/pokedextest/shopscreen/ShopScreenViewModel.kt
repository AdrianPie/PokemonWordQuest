package com.example.pokedextest.shopscreen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokedextest.data.models.Item
import com.example.pokedextest.data.models.Player
import com.example.pokedextest.data.room.playerdb.PlayerRepository
import com.example.pokedextest.repository.ItemRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.random.Random
import kotlin.random.nextInt

@HiltViewModel
class ShopScreenViewModel @Inject constructor(
    private val playerRepository: PlayerRepository,
    private val itemRepository: ItemRepository
): ViewModel() {

    private val _player = MutableStateFlow<Player>(Player())
    val player = _player.asStateFlow()

    var isDialogShown by mutableStateOf(false)
        private set

    var randomItem by mutableStateOf(Item())
        private set

    private val _chestOffsetY = MutableStateFlow<Dp>(0.dp)
    val chestOffsetY: StateFlow<Dp> get() = _chestOffsetY.asStateFlow()
    init {
        getPlayer()
        chestOffsetAnim()
    }

    fun onBuyClick(){
        viewModelScope.launch {
            delay(500)
            isDialogShown = true
        }
    }

    fun addRandomItem(){
        viewModelScope.launch(Dispatchers.IO){
            player.value.gold -= 5
            val itemLotRateList = mutableListOf<Item>()
            val itemList = itemRepository.itemList()
            for(i in itemList)
                repeat(i.lotRate) {
                    itemLotRateList.add(i)
                }
            val randomNumber = Random.nextInt(0 until itemLotRateList.size)
            val item = itemLotRateList[randomNumber]
            val player = _player.value
            player.weaponList += item
            playerRepository.update(player)
                randomItem = item
            delay(1000)
            getPlayer()
        }


    }


    fun onDismissDialog(){
        isDialogShown = false
    }
     fun getPlayer(){
        viewModelScope.launch(Dispatchers.IO) {
            val player = playerRepository.get(1)!!
            _player.value = player
        }
    }
    private fun chestOffsetAnim(){
        viewModelScope.launch(Dispatchers.IO) {
            while (true){
                _chestOffsetY.value += 15.dp
                delay(1000)
                _chestOffsetY.value -= 15.dp
                delay(1000)
            }
        }
    }
}