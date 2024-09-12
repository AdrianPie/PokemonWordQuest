package com.example.pokedextest.data.room.playerdb

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.pokedextest.data.models.Player


@Database(entities = [Player::class], version = 2, exportSchema = false)
@TypeConverters(Converters::class)
abstract class PlayerDatabase : RoomDatabase() {

    abstract fun playerDao(): PlayerDao

}