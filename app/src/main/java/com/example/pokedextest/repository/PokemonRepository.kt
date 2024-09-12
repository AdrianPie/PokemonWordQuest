package com.example.pokedextest.repository

import com.example.pokedextest.data.remote.PokeApi
import com.example.pokedextest.util.Resource
import com.example.pokedextest.data.remote.responses.Pokemon
import com.example.pokedextest.data.remote.responses.PokemonList
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject

@ActivityScoped
class PokemonRepository @Inject constructor(
    private val api: PokeApi
){

    suspend fun getPokemonList(limit: Int, offset: Int) : Resource<PokemonList> {
        val response = try {
            api.getPokemonList(limit, offset)
        } catch (e: Exception) {
            return Resource.Error(null,"An unknown error occurred.")
        }
        return Resource.Success(response)
    }

    suspend fun getPokemonInfo(pokemonName: String) : Resource<Pokemon> {
        val response = try {
            api.getPokemonInfo(pokemonName)
        } catch (e: Exception) {
            return Resource.Error(null,"An unknown error occurred.")
        }
        return Resource.Success(response)
    }

}