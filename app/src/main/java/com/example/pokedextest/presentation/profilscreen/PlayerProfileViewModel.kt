package com.example.pokedextest.presentation.profilscreen

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokedextest.R
import com.example.pokedextest.data.models.Player
import com.example.pokedextest.data.room.playerdb.PlayerRepository
import com.example.pokedextest.repository.ItemRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlayerProfileViewModel @Inject constructor(
    private val playerRepository: PlayerRepository,
    private val itemRepository: ItemRepository
): ViewModel() {


    private val _player: MutableStateFlow<Player> = MutableStateFlow(Player(avatar = R.drawable.player))
    val player: StateFlow<Player>  get() = _player.asStateFlow()

    private val _maxStat = MutableStateFlow(0)
    val maxState = _maxStat.asStateFlow()

    private val _mappedStats = MutableStateFlow<Map<String,Pair<Int,Color>>>(emptyMap())
    val mappedStats = _mappedStats.asStateFlow()

    private val _weaponEquipped = MutableStateFlow(itemRepository.basicAxe)
    val weaponEquipped = _weaponEquipped.asStateFlow()

    init {
        getPlayer()

    }

    private fun getPlayer(){
        viewModelScope.launch(Dispatchers.IO) {
            val player = playerRepository.get(1)
            if (player != null){
                _player.value = player
                createMaxStat()
                weaponEquipped()
                createMappedStats()
            }
        }
    }
    private fun weaponEquipped(){
        viewModelScope.launch(Dispatchers.IO) {
            val player = _player.value

            if (player.weaponEquipped != R.drawable.add_24px) {
                when(player.weaponEquipped){
                    R.drawable.basicaxe ->{
                        player.weaponAtk = itemRepository.basicAxe.atk
                        _weaponEquipped.value = itemRepository.basicAxe
                    }
                    R.drawable.dragonsword ->{
                        player.weaponAtk = itemRepository.dragonSword.atk
                        _weaponEquipped.value = itemRepository.dragonSword
                    }
                    R.drawable.fireclub ->{
                            player.weaponAtk = itemRepository.fireClub.atk
                            _weaponEquipped.value = itemRepository.fireClub
                    }
                    R.drawable.firesword ->{
                            player.weaponAtk = itemRepository.fireSword.atk
                            _weaponEquipped.value = itemRepository.fireSword
                    }
                    R.drawable.frozencrossbow ->{
                            player.weaponAtk = itemRepository.frozenCrossbow.atk
                            _weaponEquipped.value = itemRepository.frozenCrossbow
                    }
                }
                playerRepository.update(player)
                createMappedStats()
            }
        }
    }
    fun updatePlayer(exp: String?, weapon: Int?, soldItem: Int?){
        viewModelScope.launch(Dispatchers.IO) {
            val player = playerRepository.get(1)

            if (player != null && player.exp >= 10 && exp != null){
                when(exp){
                    "atk" -> player.atk += 5
                    "def" -> player.def += 5
                    "hp" -> player.vit += 5
                }
                player.exp -= 10
            }
            if (weapon != null && player != null){
                player.weaponEquipped = weapon
            }
            if(soldItem != null) {
                val itemList = player?.weaponList
                itemList?.removeAt(soldItem)
                player!!.weaponList = itemList!!
                player.gold += 5
            }
            playerRepository.update(player!!)
            getPlayer()
        }
    }
    private fun createMaxStat(){
        val playerInfo = player.value
        _maxStat.value = listOf(playerInfo.atk, playerInfo.def, playerInfo.vit).maxOf { it }
    }

    private fun createMappedStats(){
        val playerInfo = player.value
        _mappedStats.value = mapOf(
            "hp" to Pair(playerInfo.vit, Color.Red),
            "atk" to Pair((playerInfo.atk + playerInfo.weaponAtk), Color.Yellow),
            "def" to Pair(playerInfo.def, Color(R.color.black))
        )


    }
}