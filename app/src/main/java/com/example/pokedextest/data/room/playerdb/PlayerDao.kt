package com.example.pokedextest.data.room.playerdb

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

import com.example.pokedextest.data.models.Player

@Dao
interface PlayerDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPlayer(player: Player)

    @Update
    suspend fun updatePlayer(player: Player)

    @Delete
    suspend fun deletePlayer(player: Player)

    @Query("SELECT * FROM playerList ORDER BY idPlayer DESC")
    fun getAllPlayers(): MutableList<Player>

    @Query("SELECT * FROM playerList WHERE idPlayer like :id")
    fun getPlayer(id : Int) : Player
}