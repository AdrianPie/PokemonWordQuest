package com.example.pokedextest.data.room.playerdb

import androidx.room.TypeConverter
import com.example.pokedextest.data.models.Item
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {
    @TypeConverter
    fun fromItemList(itemList: List<Item>): String {
        val gson = Gson()
        return gson.toJson(itemList)
    }

    @TypeConverter
    fun toItemList(itemListString: String): List<Item> {
        val gson = Gson()
        val type = object : TypeToken<List<Item>>() {}.type
        return gson.fromJson(itemListString, type)
    }
}