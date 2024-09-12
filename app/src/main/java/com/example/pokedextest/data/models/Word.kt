package com.example.pokedextest.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "word_list")
data class Word(
    @PrimaryKey(autoGenerate = true)
    val idWord: Int = 0,
    val polish: String = "",
    val english: String = "",
    val spanish: String = "",
    val difficulty: Int = 0
) {
}