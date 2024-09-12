package com.example.pokedextest.splashscreen

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokedextest.data.datastore.DataStoreRepository
import com.example.pokedextest.data.remote.responses.Pokemon
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class SplashViewModel @Inject constructor(
    private val repository: DataStoreRepository
) : ViewModel() {



    private val _isLoading: MutableState<Boolean> = mutableStateOf(true)
    val isLoading: State<Boolean> = _isLoading

    private val _startDestination: MutableState<String> = mutableStateOf("welcome_screen")
    val startDestination: State<String> = _startDestination

    init {
        viewModelScope.launch {
            repository.readOnBoardingProcess().collect { stage ->
                _isLoading.value = false
                when(stage) {
                    "welcome" -> _startDestination.value = "welcome_screen"
                    "menu" -> _startDestination.value = "pokemon_menu_screen"
                    "create_character" -> _startDestination.value = "create_character_screen"
                    else -> _startDestination.value = "welcome_screen"
                }
                Log.d("dupa", ": ${_startDestination.value}")
            }

        }
    }

}