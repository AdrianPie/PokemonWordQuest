package com.example.pokedextest.welcomescreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokedextest.data.datastore.DataStoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WelcomeViewModel@Inject constructor(
    private val repository: DataStoreRepository
) : ViewModel() {

    fun saveOnBoardingState(completed: Boolean) {
        viewModelScope.launch {
            repository.saveOnBoardingState(completed)
        }
    }
    fun saveOnBoardingProcess(stage: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.saveOnBoardingProcess(stage)
        }
    }
}