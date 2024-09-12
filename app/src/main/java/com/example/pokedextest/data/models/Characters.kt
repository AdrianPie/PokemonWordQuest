package com.example.pokedextest.data.models

class Characters {
    data class CharacterClass(
        val name: String,
        val vitality: Int,
        val attack: Int,
        val defense: Int
    )

    data class Avatar(
        val name: String,
        val imageResId: Int
    )
}