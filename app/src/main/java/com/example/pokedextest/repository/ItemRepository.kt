package com.example.pokedextest.repository

import com.example.pokedextest.R
import com.example.pokedextest.data.models.Item
import javax.inject.Inject

class ItemRepository@Inject constructor(){
    val fireSword = Item("Fire Sword", R.drawable.firesword, 15, 10, 1)
    val basicAxe = Item("Basic Axe", R.drawable.basicaxe, 5,3,15)
    val dragonSword = Item("Dragon Sword", R.drawable.dragonsword,10,8,3)
    val fireClub = Item("Fire Club", R.drawable.fireclub,19,3,5)
    val frozenCrossbow = Item("Frozen Crossbow", R.drawable.frozencrossbow, 25, 0,2)


    fun itemList(): List<Item>{
        val items = mutableListOf<Item>()
        items.addAll(listOf(fireSword, basicAxe, dragonSword, fireClub, frozenCrossbow))
        return items
    }



}
