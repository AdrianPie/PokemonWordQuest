package com.example.pokedextest.data.models

import androidx.annotation.DrawableRes

data class Item(
    val name: String ="",
    @DrawableRes
    val icon: Int = 0,
    val atk: Int = 0,
    val def: Int = 0,
    val lotRate: Int = 0,
)
