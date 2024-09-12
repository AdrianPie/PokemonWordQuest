package com.example.pokedextest.data.models

import androidx.annotation.DrawableRes
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "playerList")
data class Player(
    @PrimaryKey(autoGenerate = true)
    val idPlayer: Int = 0,
    val name: String = "",
    var exp: Int = 0,
    var weaponAtk: Int = 0,
    var atk: Int = 0,
    var def: Int = 0,
    var vit: Int = 0,
    var gold: Int = 0,
    var weaponList: MutableList<Item> = mutableListOf<Item>(),
    @DrawableRes
    var weaponEquipped: Int = 0,
    @DrawableRes
    val avatar: Int = 0
) {
}