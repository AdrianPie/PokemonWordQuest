package com.example.pokedextest.data.remote.responses

import com.example.pokedextest.data.remote.responses.DreamWorld
import com.example.pokedextest.data.remote.responses.Home
import com.example.pokedextest.data.remote.responses.OfficialArtwork

data class Other(
    val dream_world: DreamWorld,
    val home: Home,
    val official_artwork: OfficialArtwork
)