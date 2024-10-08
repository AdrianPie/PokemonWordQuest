package com.example.pokedextest.data.remote

import com.example.pokedextest.data.remote.responses.Pokemon
import com.example.pokedextest.data.remote.responses.PokemonList
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokeApi {
    @GET("pokemon")
    suspend fun getPokemonList(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int,
    ) : PokemonList

    @GET("pokemon/{pokemonName}")
    suspend fun getPokemonInfo(
        @Path("pokemonName") name: String
    ) : Pokemon


}