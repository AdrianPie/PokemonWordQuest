package com.example.pokedextest.createcharacterscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokedextest.data.datastore.DataStoreRepository
import com.example.pokedextest.data.models.Player
import com.example.pokedextest.data.room.playerdb.PlayerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateCharacterViewModel@Inject constructor(
    private val repository: PlayerRepository,
    private val repositoryDataStore: DataStoreRepository
) : ViewModel() {
    fun savePlayer(player: Player){
        viewModelScope.launch {
            repository.insert(player)
        }
    }
    fun saveOnBoardingProcess(stage: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repositoryDataStore.saveOnBoardingProcess(stage)
        }
    }
    fun createPlayer(name: String, atk: Int, def: Int, vit: Int, avatar: Int, gold: Int, exp: Int, weaponEquipped: Int): Player {
        return Player(name = name, atk = atk, def = def, vit = vit, avatar = avatar, gold = gold, exp = exp, weaponEquipped = weaponEquipped)
    }
}